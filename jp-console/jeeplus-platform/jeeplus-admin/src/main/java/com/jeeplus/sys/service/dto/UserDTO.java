/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jeeplus.core.service.dto.BaseDTO;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户Entity
 *
 * @author jeeplus
 * @version 2021-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;


    /**
     * 登录名
     */
    @Length(min = 1, max = 100)
    @Excel (name = "登录名")
    @Query
    private String loginName;

    /**
     * 密码
     */
    @JsonIgnore
    @Length(min = 1, max = 100)
    private String password;


    /**
     * 姓名
     */
    @Length(min = 1, max = 100)
    @Excel(name = "姓名")
    @Query(tableColumn = "a.name")
    private String name;


    /**
     * 工号
     */
    @Length(min = 1, max = 100)
    @Excel (name = "工号")
    private String no;

    /**
     * 归属公司
     */
    @NotNull(message = "归属公司不能为空")
    @Excel(name = "归属公司",  enumExportField = "name", isImportField = "false")
    @Query(type = QueryType.EQ, tableColumn = "c.id", javaField = "companyDTO.id")
    @ApiModelProperty(hidden = true)
    private OfficeDTO companyDTO;

    /**
     * 归属部门
     */
    @NotNull(message = "归属部门不能为空")
    @Excel(name = "归属部门",  enumExportField = "name", isImportField = "false")
    @Query(type = QueryType.EQ, tableColumn = "o.id", javaField = "officeDTO.id")
    @ApiModelProperty(hidden = true)
    private OfficeDTO officeDTO;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Length(min = 0, max = 100)
    @Excel (name = "邮箱")
    private String email;

    /**
     * 电话
     */
    @Length(min = 0, max = 100)
    @Excel (name = "电话")
    private String phone;

    /**
     * 手机
     */
    @Length(min = 0, max = 100)
    @Excel(name = "手机")
    private String mobile;

    /**
     * 最后登录IP
     */
    @ApiModelProperty(hidden = true)
    private String loginIp;

    /**
     * 最后登录日期
     */
    @ApiModelProperty(hidden = true)
    private Date loginDate;

    /**
     * 是否允许登录
     */
    private String loginFlag;

    /**
     * 头像
     */
    private String photo;

    /**
     * 二维码
     */
    private String qrCode;

    /**
     * 原登录名
     */
    private String oldLoginName;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 签名
     */
    private String sign;


    /**
     * 备注
     */
    private String remarks;

    /**
     * 超级管理员标志
     */
    private Boolean isAdmin;

    /**
     * 根据角色查询用户条件
     */
    @Query(type = QueryType.EQ, javaField ="roleDTO.id", tableColumn = "r.id")
    @ApiModelProperty(hidden = true)
    private RoleDTO roleDTO;

    /**
     * 根据岗位查询用户
     */
    @Query(type = QueryType.EQ, javaField ="postDTO.id", tableColumn = "p.id")
    private PostDTO postDTO;

    /**
     * 拥有角色列表
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @Excel(name = "拥有角色",  enumExportField = "name", isImportField = "false")
    private List<RoleDTO> roleDTOList = Lists.newArrayList ();

    /**
     * 拥有岗位列表
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @Excel(name = "拥有岗位",  enumExportField = "name", isImportField = "false")
    private List<PostDTO> postDTOList = Lists.newArrayList ();

    public UserDTO() {
        super();
    }

    public UserDTO(String id) {
        super(id);
    }


    /**
     * 获取包含的角色id列表
     * @return
     */
    public List<String> getRoleIdList() {
        if(roleDTOList == null) {
            return Lists.newArrayList ();
        }
        List<String> roleIdList = roleDTOList.stream ().map ( roleDTO -> {
            return roleDTO.getId ();
        } ).collect( Collectors.toList());
        return roleIdList;
    }

    /**
     * 设置角色
     * @param roleIdList
     */
    public void setRoleIdList(List<String> roleIdList) {
        for (String roleId : roleIdList) {
            RoleDTO roleDTO = new RoleDTO (roleId);
            roleDTOList.add (roleDTO);
        }
    }

    /**
     * 获取包含的岗位id列表
     * @return
     */
    public List<String> getPostIdList() {
        if(postDTOList == null) {
            return Lists.newArrayList ();
        }
        List<String> postIdList = postDTOList.stream ().map ( postDTO -> {
            return postDTO.getId ();
        } ).collect( Collectors.toList());
        return postIdList;
    }

    /**
     * 设置岗位
     * @param postIdList
     */
    public void setPostIdList(List<String> postIdList) {
        for (String postId : postIdList) {
            PostDTO postDTO = new PostDTO (postId);
            postDTOList.add (postDTO);
        }
    }


    /**
     * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
     */
    public String getRoleNames() {
        List<String> roleNames = roleDTOList.stream ().map ( roleDTO -> {
            return roleDTO.getName ();
        } ).collect( Collectors.toList());
        return StrUtil.join ( ",", roleNames );
    }

    public String getRoleIds() {
        List<String> roleIds = roleDTOList.stream ().map ( roleDTO -> {
            return roleDTO.getId ();
        } ).collect( Collectors.toList());
        return StrUtil.join ( ",", roleIds );
    }

}
