/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.notify.service.dto;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.service.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知通告Entity
 *
 * @author jeeplus
 * @version 2021-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NotifyDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    @Query(type = QueryType.EQ)
    @Size(min = 0, max = 1, message = "类型长度必须介于 0 和 1 之间")
    private String type;
    /**
     * 标题
     */
    @Query
    @Size(min = 0, max = 200, message ="标题长度必须介于 0 和 200 之间")
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 附件
     */
    @Size(min = 0, max = 2000, message = "附件长度必须介于 0 和 2000 之间")
    private String files;
    /**
     * 状态
     */
    @Query(type = QueryType.EQ)
    @Size(min = 0, max = 1, message = "状态长度必须介于 0 和 1 之间")
    private String status;
    /**
     * 已读
     */
    private String readNum;
    /**
     * 未读
     */
    private String unReadNum;
    /**
     * 是否只查询自己的通知
     */
    private boolean isSelf;
    /**
     * 本人阅读状态
     */
    @Query(type = QueryType.EQ)
    private String readFlag;

    private List<NotifyRecordDTO> notifyRecordDTOList = Lists.newArrayList();


    /**
     * 获取通知发送记录用户ID
     *
     * @return
     */
    public String getNotifyRecordIds() {
        return notifyRecordDTOList.stream ().map ( notifyRecordDTO -> {
            return notifyRecordDTO.getUserDTO ().getId ();
        } ).collect( Collectors.joining(","));
    }

    /**
     * 设置通知发送记录用户ID
     *
     * @return
     */
    public void setNotifyRecordIds(String notifyRecordIds) {
        this.notifyRecordDTOList = Lists.newArrayList();
        for (String id : StrUtil.split(notifyRecordIds, ",")) {
            NotifyRecordDTO dto = new NotifyRecordDTO ();
            dto.setNotifyDTO ( this );
            dto.setUserDTO ( new UserDTO ( id ) );
            dto.setReadFlag ( CommonConstants.NO );
            this.notifyRecordDTOList.add(dto);
        }
    }

    /**
     * 获取通知发送记录用户Name
     *
     * @return
     */
    public String getOaNotifyRecordNames() {
        return notifyRecordDTOList.stream ().map ( notifyRecordDTO -> {
            return notifyRecordDTO.getUserDTO ().getName ();
        } ).collect( Collectors.joining(","));
    }
}
