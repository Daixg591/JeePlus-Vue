/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller;

import com.google.common.collect.Lists;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.service.OfficeService;
import com.jeeplus.sys.service.dto.OfficeDTO;
import com.jeeplus.sys.service.mapstruct.OfficeWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 机构Controller
 *
 * @author jeeplus
 * @version 2021-5-15
 */

@Api("机构管理")
@RestController
@RequestMapping("/sys/office")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @Autowired
    private OfficeWrapper officeWrapper;


    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    @ApiLog("查询部门")
    @PreAuthorize("hasAnyAuthority('sys:office:view','sys:office:add','sys:office:edit')")
    @GetMapping("queryById")
    public ResponseEntity queryById(@RequestParam String id) {
        OfficeDTO officeDTO = officeWrapper.toDTO (officeService.getById (id));
        return ResponseEntity.ok (officeDTO);
    }

    /**
     * 保存或者修改部门
     * @param officeDTO
     * @return
     */
    @DemoMode
    @ApiLog("保存部门")
    @PreAuthorize ("hasAnyAuthority('sys:office:add','sys:office:edit')")
    @PostMapping("save")
    public ResponseEntity save(@Valid @RequestBody OfficeDTO officeDTO) {
        officeService.saveOrUpdate (officeWrapper.toEntity (officeDTO));
        return ResponseEntity.ok ("保存机构'" + officeDTO.getName () + "'成功");
    }

    /**
     * 删除部门
     * @param ids
     * @return
     */
    @DemoMode
    @ApiLog("删除部门")
    @PreAuthorize("hasAuthority('sys:office:del')")
    @DeleteMapping("delete")
    public ResponseEntity delete(String ids) {
        String idArray[] =ids.split(",");
        officeService.removeWithChildrenByIds ( Lists.newArrayList (idArray) );
        return ResponseEntity.ok ("删除成功！");
    }


    /**
     * 获取机构JSON数据。
     *
     * @param extId 排除的ID
     * @param type  类型（1：公司；2：部门）
     * @param showAll 是否显示不可用数据 1 显示 0 隐藏
     * @return
     */
    @ApiLog("获取部门数据")
    @GetMapping("treeData")
    public ResponseEntity treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String type, @RequestParam(required = false, defaultValue = CommonConstants.NO) String showAll) {
        List<OfficeDTO> list = officeWrapper.toDTO (officeService.list ());
        List rootTree = officeService.getRootTree (list, extId, type, showAll);
        return ResponseEntity.ok (rootTree);
    }




}
