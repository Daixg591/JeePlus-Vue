/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datalink.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.database.DataSourceConfig;
import com.jeeplus.database.datalink.domain.DataSource;
import com.jeeplus.database.datalink.domain.vo.DataSourceTreeVO;
import com.jeeplus.database.datalink.jdbc.DBPool;
import com.jeeplus.database.datalink.service.DataSourceService;
import com.jeeplus.database.datalink.service.mapstruct.DataSourceWrapper;
import com.jeeplus.sys.utils.DictUtils;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

/**
 * 数据库连接Controller
 *
 * @author 刘高峰
 * @version 2021-08-06
 */
@RestController
@RequestMapping(value = "/database/datalink/dataSource")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private DatabaseIdProvider databaseIdProvider;
    @Autowired
    private DataSourceConfig dataSourceConfig;

    /**查询实体
     */
    @PreAuthorize("hasAnyAuthority('database:datalink:dataSource:view','database:datalink:dataSource:add','database:datalink:dataSource:edit')")
    @GetMapping(value = "queryById")
    public ResponseEntity queryById(String id) {
        return ResponseEntity.ok ( dataSourceService.getById ( id ) );
    }

    @PreAuthorize("hasAuthority('database:datalink:dataSource:list')")
    @GetMapping(value = "list")
    public ResponseEntity list(DataSource dataSource, Page <DataSource> page) throws Exception {
        QueryWrapper <DataSource> queryWrapper = QueryWrapperGenerator.buildQueryCondition (dataSource, DataSource.class);
        IPage <DataSource> result = dataSourceService.page (page, queryWrapper);
        return ResponseEntity.ok (result);
    }


    @GetMapping(value = "treeData")
    public ResponseEntity treeData2() {
        List<DataSourceTreeVO> treeList = Lists.newArrayList();
        List <DataSourceTreeVO> treeRoots = Lists.newArrayList();
        String dbType ;
        try{
            dbType = databaseIdProvider.getDatabaseId( SpringUtil.getBean(javax.sql.DataSource.class));

        }catch (Exception e){
            dbType = "mysql";
        }
        treeRoots.add(new DataSourceTreeVO ("master-parent","默认数据源","0","","host", true));

        treeList.add(new DataSourceTreeVO (dataSourceConfig.primary,"本地数据库","master-parent",dataSourceConfig.primary,"db", dbType));

        List<DataSource> list = dataSourceService.list ();
        HashSet <String> set = new HashSet();
        for (int i = 0; i < list.size(); i++) {
            DataSource e = list.get(i);
            treeList.add(new DataSourceTreeVO (e.getId(),e.getName(),e.getHost(),e.getEnName(),"db", e.getType()));
            set.add(e.getHost());
        }
        for (String host : set) {
            treeRoots.add(new DataSourceTreeVO (host,host,"0","","host", true));
        }
        List rootTree = getRootTree(treeRoots,treeList);
        return ResponseEntity.ok ( rootTree );
    }



    private List<DataSourceTreeVO> getRootTree(List<DataSourceTreeVO> rootTrees, List<DataSourceTreeVO> list) {
        List<DataSourceTreeVO> trees = Lists.newArrayList();
        for (DataSourceTreeVO root : rootTrees) {
                trees.add(getChildOfTree(root, list));
        }
        return trees;
    }

    private DataSourceTreeVO getChildOfTree(DataSourceTreeVO area, List<DataSourceTreeVO> areaList) {
        area.setChildren(Lists.newArrayList());
        for (DataSourceTreeVO child : areaList) {
                if (child.getParentId().equals(area.getId())) {
                    area.getChildren().add(getChildOfTree(child, areaList));
                }
        }
        return area;
    }

    /**
     * 保存数据库连接
     */
    @PreAuthorize ("hasAnyAuthority('database:datalink:dataSource:add','database:datalink:dataSource:edit')")
    @PostMapping(value = "save")
    public ResponseEntity save(@Valid @RequestBody DataSource dataSource)  {
        String oldName = "";
        if( StrUtil.isNotBlank(dataSource.getId())){
             oldName = dataSourceService.getById (dataSource.getId()).getEnName();
        }

        String driver = DictUtils.getDictValue(dataSource.getType(), "db_driver","mysql");
        dataSource.setDriver(driver);
        dataSource.setUrl(dataSourceService.toUrl(dataSource.getType(), dataSource.getHost(), dataSource.getPort(), dataSource.getDatabaseName ()));
        //新增或编辑表单保存
        dataSourceService.saveOrUpdate (dataSource);
        if(StrUtil.isNotBlank(oldName)){
            DBPool.getInstance().remove (oldName);
        }
        DBPool.getInstance().addDs ( DataSourceWrapper.INSTANCE.toDTO ( dataSource));
        return ResponseEntity.ok ( "保存数据库连接成功" );
    }


    /**
     * 批量删除数据库连接
     */
    @PreAuthorize("hasAuthority('database:datalink:dataSource:del')")
    @DeleteMapping(value = "delete")
    public ResponseEntity delete(@RequestParam(value = "ids", required = false)String ids) {
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            DataSource dataSource = dataSourceService.getById (id);
            DBPool.getInstance().remove (dataSource.getEnName());
            dataSourceService.removeById ( id );
        }
        return ResponseEntity.ok ( "删除数据库连接成功" );
    }




    /**
     * 验证数据库唯一key是否存在
     * @param oldEnName
     * @param enName
     * @return
     */
    @GetMapping(value = "checkEnName")
    public ResponseEntity<String> checkLoginName(@RequestParam(value = "oldEnName", required = false)String oldEnName, @RequestParam(value = "enName", required = false)String enName) {
        if (enName !=null && enName.equals(oldEnName)) {
            return ResponseEntity.ok ("验证通过!");
        } else if (enName !=null && dataSourceService.lambdaQuery ().eq ( DataSource::getEnName, enName ).one () == null) {
            return ResponseEntity.ok ( "验证通过!" );
        }
        return ResponseEntity.ok ("数据库连接英文名已存在");
    }

    /**
     * 测试数据库连接
     * @param dataSource
     * @return
     */
    @PostMapping("/test")
    public ResponseEntity test(@RequestBody DataSource dataSource) {
        if (StrUtil.isBlank(dataSource.getType ()) || StrUtil.isBlank(dataSource.getHost ()) || StrUtil.isBlank(dataSource.getDatabaseName ()) || StrUtil.isBlank(dataSource.getUsername ())) {
            return ResponseEntity.badRequest ().body ( "配置信息不全" );
        }
        if (DBPool.getInstance().test(dataSourceService.getDriver(dataSource.getType ()), dataSourceService.toUrl(dataSource.getType (), dataSource.getHost (), dataSource.getPort (), dataSource.getDatabaseName ()), dataSource.getUsername (), dataSource.getPassword ())) {
            return ResponseEntity.ok ("连接成功"  );
        } else {
            return ResponseEntity.badRequest ().body ( "连接失败" );
        }
    }

}
