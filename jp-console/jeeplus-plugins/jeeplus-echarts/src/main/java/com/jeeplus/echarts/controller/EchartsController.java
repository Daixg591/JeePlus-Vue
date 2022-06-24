/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.echarts.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.database.datamodel.domain.DataMeta;
import com.jeeplus.database.datamodel.service.DataMetaService;
import com.jeeplus.echarts.domain.Echarts;
import com.jeeplus.echarts.service.EchartsService;
import com.jeeplus.echarts.service.dto.EchartsDTO;
import com.jeeplus.echarts.service.mapstruct.EchartsWrapper;
import com.jeeplus.echarts.utils.OptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


/**
 * 图表组件Controller
 *
 * @author 刘高峰
 * @version 2021-07-13
 */
@RestController
@RequestMapping(value = "/echarts")
public class EchartsController {


    @Autowired
    private DataMetaService dataMetaService;

    @Autowired
    private EchartsService echartsService;

    @Autowired
    private EchartsWrapper echartsWrapper;


    @GetMapping(value = "queryById")
    public ResponseEntity<EchartsDTO> preview(String id) {
        Echarts echarts = echartsService.getById ( id );
        return ResponseEntity.ok ( echartsWrapper.toDTO (echarts) );
    }

    /**
     * 图表组件列表数据
     */
    @GetMapping(value = "list")
    public ResponseEntity<IPage <EchartsDTO>> list(EchartsDTO echarts, Page <EchartsDTO> page) throws Exception {
        QueryWrapper <EchartsDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( echarts, EchartsDTO.class );
        IPage <EchartsDTO> result = echartsService.findPage ( page, queryWrapper );
        return ResponseEntity.ok ( result );
    }

    /**
     * 查看，增加，编辑图表组件表单页面
     */
    @GetMapping(value = "queryDesignById")
    public ResponseEntity queryDesignById(String id) {
        Echarts echarts = echartsService.getById ( id );

        List <DataMeta> xColumnList = Lists.newArrayList ( );
        List <DataMeta> yColumnList = Lists.newArrayList ( );
        List <DataMeta> columnList = dataMetaService.lambdaQuery ( ).eq ( DataMeta::getDataSetId, echarts.getDataSetId ( ) ).list ( );
        for (String xname : echarts.getXnames ( ).split ( "," )) {
            for (DataMeta column : columnList) {
                if ( xname.equals ( column.getName ( ) ) ) {
                    xColumnList.add ( column );
                }
            }
        }
        for (String yName : echarts.getYnames ( ).split ( "," )) {
            for (DataMeta column : columnList) {
                if ( yName.equals ( column.getName ( ) ) ) {
                    yColumnList.add ( column );
                }
            }
        }
        return ResponseUtil.newInstance ( ).add ( "xColumnList", xColumnList ).add ( "yColumnList", yColumnList ).add ( "echarts", echartsWrapper.toDTO ( echarts ) ).ok ( );
    }

    /**
     * 保存图表组件
     */
    @PostMapping(value = "save")
    public ResponseEntity save(@Valid  @RequestBody EchartsDTO echartsDTO) {
        Echarts echarts = echartsWrapper.toEntity ( echartsDTO );
        echartsService.saveOrUpdate ( echarts );
        return ResponseEntity.ok ( "保存成功" );
    }


    /**
     * 批量删除图表组件
     */
    @DeleteMapping(value = "delete")
    public ResponseEntity delete(String ids) {
        String idArray[] = ids.split ( "," );
        echartsService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok ( "删除成功" );
    }

    /**
     * 获取组件option
     */
    @GetMapping(value = "getChartData/{id}")
    public ResponseEntity getChartData(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
        Echarts echarts = echartsService.getById ( id );
        String xnames = echarts.getXnames ( );
        String ynames = echarts.getYnames ( );
        return ResponseUtil.newInstance ( ).add ( "echarts", echartsWrapper.toDTO ( echarts ) ).add ( "chartData", OptionUtil.getChartData ( echarts.getDataSetId ( ), xnames, ynames, request ) ).ok ( );
    }

    /**
     * 获取组件option
     */
    @GetMapping(value = "mergeChartData")
    public ResponseEntity mergeChartData(String dataSetId, String xnames, String ynames, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok ( OptionUtil.getChartData ( dataSetId, xnames, ynames, request ) );
    }

}
