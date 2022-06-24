/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.office.conroller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.office.domain.DocTemplate;
import com.jeeplus.office.service.DocTemplateService;
import com.jeeplus.office.service.dto.DocTemplateDTO;
import com.jeeplus.office.service.mapstruct.DocTemplateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 文档Controller
 *
 * @author sunyinhui
 * @version 2021-06-23
 */
@Api(tags = "文档")
@RestController
@RequestMapping(value = "/wps/docTemplate")
public class DocTemplateController {

    @Autowired
    private DocTemplateService docTemplateService;
    @Autowired
    private DocTemplateWrapper docTemplateWrapper;

    /**
     * 文档列表数据
     */
    @ApiOperation("查询文档列表")
    @PreAuthorize("hasAuthority('wps:docTemplate:list')")
    @GetMapping("list")
    public ResponseEntity <IPage <DocTemplateDTO>> list(DocTemplateDTO docTemplateDTO, Page <DocTemplateDTO> page) throws Exception {
        QueryWrapper <DocTemplateDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( docTemplateDTO, DocTemplateDTO.class );
        IPage <DocTemplateDTO> result = docTemplateService.findPage ( page, queryWrapper );
        return ResponseEntity.ok ( result );
    }

    /**
     * 根据Id获取文档数据
     */
    @ApiOperation("根据Id获取文档数据")
    @PreAuthorize ("hasAnyAuthority('wps:docTemplate:view','wps:docTemplate:add','wps:docTemplate:edit')")
    @GetMapping("queryById")
    public ResponseEntity <DocTemplateDTO> queryById(String id) {
        DocTemplateDTO docTemplateDTO = docTemplateWrapper.toDTO ( docTemplateService.getById ( id )  );
        return ResponseEntity.ok ( docTemplateDTO );
    }

    /**
     * 保存文档
     */
    @ApiOperation("保存文档")
    @PreAuthorize ("hasAnyAuthority('wps:docTemplate:add','wps:docTemplate:edit')")
    @PostMapping("save")
    public ResponseEntity <String> save(@RequestBody @Valid DocTemplateDTO docTemplateDTO) {
        DocTemplate docTemplate = docTemplateWrapper.toEntity ( docTemplateDTO );
        //新增或编辑表单保存
        docTemplateService.saveOrUpdate ( docTemplate );
        return ResponseEntity.ok ( "保存文档成功" );
    }


    /**
     * 批量删除文档
     */
    @ApiOperation("批量删除文档")
    @PreAuthorize ("hasAuthority('wps:docTemplate:del')")
    @DeleteMapping("delete")
    public ResponseEntity <String> delete(String ids) {
        String idArray[] = ids.split ( "," );
        docTemplateService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok ( "删除文档成功" );
    }


}
