package com.jeeplus.tools.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmailRequest {
    /**
     * 邮件地址
     */
   private String emailAddress;

    /**
     * 邮件标题
     */
   private String title;

    /**
     * 邮件内容
     */
   private String content;
}
