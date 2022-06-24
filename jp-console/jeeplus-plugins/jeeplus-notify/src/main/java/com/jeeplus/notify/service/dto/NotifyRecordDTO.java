/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.notify.service.dto;

import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 通知通告记录Entity
 *
 * @author jeeplus
 * @version 2021-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NotifyRecordDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 通知通告ID
     */
    private NotifyDTO notifyDTO;
    /**
     * 接受人
     */
    private UserDTO userDTO;
    /**
     *  阅读标记（0：未读；1：已读）
     */
    private String readFlag;
    /**
     * 阅读时间
     */
    private Date readDate;



}
