/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.notify.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.notify.service.NotifyService;
import com.jeeplus.notify.service.dto.NotifyDTO;
import com.jeeplus.notify.service.dto.NotifyRecordDTO;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 通知通告Controller
 *
 * @author jeeplus
 * @version 2021-05-16
 */
@RestController
@RequestMapping("/notify")
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    /**
     * 通告列表数据
     */
    @PreAuthorize("hasAuthority('notify:list')")
    @GetMapping("list")
    public ResponseEntity data(NotifyDTO notifyDTO, boolean isSelf, Page <NotifyDTO> page) throws Exception {
        QueryWrapper <NotifyDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( notifyDTO, NotifyDTO.class );
        IPage <NotifyDTO> result = notifyService.findPage ( page, UserUtils.getCurrentUserDTO ( ).getId ( ), isSelf, null, queryWrapper );
        return ResponseEntity.ok ( result );
    }

    /**
     * 查询数据
     */
    @GetMapping("queryById")
    public ResponseEntity queryById(String id, boolean isSelf) {
        if ( isSelf ) {
            notifyService.updateReadFlag ( id );

        }
        NotifyDTO notifyDTO = notifyService.getDetail ( id );
        return ResponseEntity.ok ( notifyDTO);
    }

    @PreAuthorize ("hasAnyAuthority('notify:add','notify:edit')")
    @PostMapping("save")
    public ResponseEntity save(@Valid @RequestBody NotifyDTO notifyDTO) {
        // 如果是修改，则状态为已发布，则不能再进行操作
        if ( StrUtil.isNotBlank ( notifyDTO.getId ( ) ) ) {
            NotifyDTO e = notifyService.getById ( notifyDTO.getId ( ) );
            if ( CommonConstants.YES.equals ( e.getStatus ( ) ) ) {
                return ResponseEntity.badRequest ( ).body ( "已发布，不能操作！" );
            }else if(!UserUtils.getCurrentUserDTO ().getId ().equals ( e.getCreateBy () )){
                return ResponseEntity.badRequest ().body ( "你只能编辑自己的公告!" );
            }
        }
        notifyService.saveOrUpdate ( notifyDTO );
        return ResponseEntity.ok ( "保存通知'" + notifyDTO.getTitle ( ) + "'成功" );
    }

    @PreAuthorize("hasAuthority('notify:del')")
    @DeleteMapping("delete")
    public ResponseEntity <String> delete(String ids) {
        String idArray[] = ids.split ( "," );
        notifyService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok ( "删除通知成功" );
    }

}
