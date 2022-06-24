/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.notify.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知通告Entity
 *
 * @author jeeplus
 * @version 2021-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_notify")
public class Notify extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    private String type;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 附件
     */
    private String files;
    /**
     * 状态
     */
    private String status;

    /**
     * 描述
     */
    private String remarks;


}
