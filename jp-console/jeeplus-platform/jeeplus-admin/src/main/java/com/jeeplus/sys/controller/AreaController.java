/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller;

import com.google.common.collect.Lists;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.sys.service.dto.AreaDTO;
import com.jeeplus.sys.domain.Area;
import com.jeeplus.sys.service.AreaService;
import com.jeeplus.sys.service.mapstruct.AreaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 区域Controller
 *
 * @author jeeplus
 * @version 2021-5-15
 */
@RestController
@RequestMapping("/sys/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaWrapper areaWrapper;

    /**
     * 查询区域列表
     * @return
     */
    @ApiLog("查询区域列表")
    @PreAuthorize("hasAuthority('sys:area:list')")
    @GetMapping("list")
    public ResponseEntity <List <Area>> list() {
        return ResponseEntity.ok ( ).body ( areaService.findAll ( ) );
    }

    /**
     * 根据id查询区域
     * @param id
     * @return
     */
    @ApiLog("查询区域")
    @PreAuthorize ("hasAnyAuthority('sys:area:view', 'sys:area:add', 'sys:area:edit')")
    @GetMapping("queryById")
    public ResponseEntity <AreaDTO> queryById(String id) {

        return ResponseEntity.ok ( areaWrapper.toDTO ( areaService.getById ( id ) ) );
    }

    /**
     * 保存区域
     * @param areaDTO
     * @return
     */
    @DemoMode
    @ApiLog("保存区域")
    @PreAuthorize ("hasAnyAuthority('sys:area:add', 'sys:area:edit')")
    @PostMapping("save")
    public ResponseEntity <String> save(@RequestBody AreaDTO areaDTO) {
        areaService.saveOrUpdate ( areaWrapper.toEntity ( areaDTO ) );
        return ResponseEntity.ok ( "保存成功！" );
    }

    /**
     * 删除区域
     * @param ids
     * @return
     */
    @DemoMode
    @ApiLog("删除区域")
    @PreAuthorize("hasAuthority('sys:area:del')")
    @DeleteMapping("delete")
    public ResponseEntity <String> delete(String ids) {
        String idArray[] = ids.split ( "," );
        areaService.removeWithChildrenByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok ( "删除区域成功！" );
    }

    /**
     * 获取区域JSON数据。
     * @param extId 排除的ID
     * @return
     */
    @ApiLog("获取区域数据")
    @GetMapping("treeData")
    public ResponseEntity <List <Area>> treeData(@RequestParam(required = false) String extId) {
        List <Area> rootTree = areaService.treeData ( extId );
        return ResponseEntity.ok ( rootTree );
    }

}
