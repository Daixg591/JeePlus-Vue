/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.notify.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
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
@TableName("plugin_notify_record")
public class NotifyRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 通知通告ID
     */
    private String notifyId;
    /**
     * 接受人
     */
    private String userId;
    /**
     *  阅读标记（0：未读；1：已读）
     */
    private String readFlag;
    /**
     * 阅读时间
     */
    private Date readDate;




}
