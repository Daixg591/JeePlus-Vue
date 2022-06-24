/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程表单Entity
 * @author 刘高峰
 * @version 2021-02-02
 */


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_form_def_json")
public class FormDefinitionJson extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 表单定义id
	 */
	@Query(type=QueryType.EQ)
	private String formDefinitionId;
	/**
	 * 流程表单结构体
	 */
	@TableField("`json`")
	private String json;
	private Integer version;		// 版本号
	private String status;		// 状态
	private String isPrimary;		// 是否主版本

	public FormDefinitionJson() {
		super();
	}

	public FormDefinitionJson(String id){
		super(id);
	}


}
