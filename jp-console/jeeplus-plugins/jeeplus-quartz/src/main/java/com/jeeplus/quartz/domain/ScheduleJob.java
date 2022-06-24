/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.quartz.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;


/**
 * 定时任务Entity
 *
 * @author lgf
 * @version 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_schedule")
public class ScheduleJob extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 任务名
     */
    @Query
    @NotEmpty
    private String name;
    /**
     * 任务组
     */
    private String jobGroup;
    /**
     * 定时规则
     */
    @NotEmpty
    private String cronExpression;
    /**
     * 启用状态
     */
    @NotEmpty
    private String status;
    /**
     * 通知用户
     */
    private String isInfo;
    /**
     * 任务类
     */
    @NotEmpty
    private String className;

    /**
     * 备注
     */
    private String remarks;

}
