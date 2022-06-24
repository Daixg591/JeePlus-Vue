/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.notify.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.notify.domain.NotifyRecord;
import com.jeeplus.notify.mapper.NotifyRecordMapper;
import com.jeeplus.notify.service.dto.NotifyDTO;
import com.jeeplus.notify.service.dto.NotifyRecordDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 通知通告Service
 *
 * @author jeeplus
 * @version 2021-05-16
 */
@Service
@Transactional
public class NotifyRecordService extends ServiceImpl<NotifyRecordMapper, NotifyRecord> {

    public List<NotifyRecordDTO> findListByNotifyId (String notifyId) {
        return baseMapper.findListByNotifyId ( notifyId );
    }
}
