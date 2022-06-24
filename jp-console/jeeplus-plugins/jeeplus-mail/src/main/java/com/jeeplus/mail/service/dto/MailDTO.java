/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.service.dto;

import com.google.common.collect.Lists;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 发件箱Entity
 * @author liugaofeng
 * @version 2021-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MailDTO extends BaseDTO {

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
    /**
     * 收件箱列表
     */
    private List<MailBoxDTO> mailBoxList = Lists.newArrayList();
    /**
     * 发件箱列表
     */
    private List<MailComposeDTO> mailComposeList = Lists.newArrayList();

}
