/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * 发件箱Entity
 * @author liugaofeng
 * @version 2021-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_mail")
public class Mail extends BaseEntity{

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容概要
     */
    private String overview;
    /**
     * 内容
     */
    private String content;

}
