/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.calendar.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 日历Entity
 *
 * @author liugf
 * @version 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_calendar")
public class MyCalendar {

    private static final long serialVersionUID = 1L;

    /**
     * 实体主键
     */
    @TableId
    private String id;

    /**
     * 事件标题
     */
    private String title;

    /**
     * 事件开始时间
     */
    private Date start;

    /**
     * 事件结束时间
     */
    private Date end;

    /**
     * 是否为全天时间
     */
    private String allDay;

    /**
     * 时间的背景色
     */
    private String color;

    /**
     * 所属用户
     */
    private String userId;

}
