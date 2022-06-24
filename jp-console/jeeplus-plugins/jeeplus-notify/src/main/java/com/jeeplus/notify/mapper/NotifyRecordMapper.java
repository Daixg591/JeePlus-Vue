/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.notify.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.notify.domain.NotifyRecord;
import com.jeeplus.notify.service.dto.NotifyRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知通告记录MAPPER接口
 *
 * @author jeeplus
 * @version 2021-05-16
 */
public interface NotifyRecordMapper extends BaseMapper <NotifyRecord> {

    List <NotifyRecordDTO> findListByNotifyId(@Param ( "notifyId" ) String notifyId);
}
