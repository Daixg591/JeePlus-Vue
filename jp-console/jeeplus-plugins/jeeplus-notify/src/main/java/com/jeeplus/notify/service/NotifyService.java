/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.notify.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.notify.domain.Notify;
import com.jeeplus.notify.domain.NotifyRecord;
import com.jeeplus.notify.mapper.NotifyMapper;
import com.jeeplus.notify.service.dto.NotifyDTO;
import com.jeeplus.notify.service.mapstruct.NotifyRecordWrapper;
import com.jeeplus.notify.service.mapstruct.NotifyWrapper;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 通知通告Service
 *
 * @author jeeplus
 * @version 2021-05-16
 */
@Service
@Transactional
public class NotifyService extends ServiceImpl <NotifyMapper, Notify> {

    @Autowired
    private NotifyRecordService notifyRecordService;

    /**
     * 根据id获取通知
     */
    public NotifyDTO getById(String id) {
        return baseMapper.getById ( id );
    }

    /**
     * 自定义分页检索
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage <NotifyDTO> findPage(Page <NotifyDTO> page, String currentUserId, boolean isSelf, String readFlag, QueryWrapper queryWrapper) {
        queryWrapper.eq ( "a.del_flag", 0 ); // 排除已经删除
        return baseMapper.findList ( page, currentUserId, isSelf, readFlag, queryWrapper );
    }

    /**
     * 获取通知发送记录
     *
     * @param id
     * @return
     */
    public NotifyDTO getDetail(String id) {
        NotifyDTO notifyDTO = baseMapper.getById ( id );
        List notifyRecordList = notifyRecordService.findListByNotifyId ( id );
        notifyDTO.setNotifyRecordDTOList ( notifyRecordList );
        return notifyDTO;
    }


    /**
     * 获取通知数目
     *
     * @return
     */
    public Long findCount(String currentUserId, boolean isSelf, String readFlag) {
        return baseMapper.findCount ( currentUserId, isSelf, readFlag );
    }

    /**
     * 保存通知
     *
     * @param notifyDTO
     * @return
     */
    public boolean saveOrUpdate(NotifyDTO notifyDTO) {
        Notify notify = NotifyWrapper.INSTANCE.toEntity ( notifyDTO );
        super.saveOrUpdate ( notify );
        notifyDTO.setId ( notify.getId () );
        // 更新发送接受人记录
        notifyRecordService.lambdaUpdate ( ).eq ( NotifyRecord::getNotifyId, notify.getId ( ) ).remove ( );
        notifyRecordService.saveBatch ( NotifyRecordWrapper.INSTANCE.toEntity ( notifyDTO.getNotifyRecordDTOList ( ) ) );
        return true;
    }

    /**
     * 更新阅读状态
     */
    public void updateReadFlag(String id) {
        notifyRecordService.lambdaUpdate ( ).eq ( NotifyRecord::getNotifyId, id )
                .eq ( NotifyRecord::getUserId, UserUtils.getCurrentUserDTO ( ).getId ( ) ).ne ( NotifyRecord::getReadFlag, CommonConstants.YES )
                .set ( NotifyRecord::getReadDate, new Date ( ) ).set ( NotifyRecord::getReadFlag, CommonConstants.YES ).update ( );
    }
}
