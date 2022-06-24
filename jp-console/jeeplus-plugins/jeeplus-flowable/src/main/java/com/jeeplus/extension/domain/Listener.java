/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 监听器Entity
 * @author 刘高峰
 * @version 2021-10-14
 */


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_listener")
public class Listener extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	@Query
	private String name;
	/**
	 * 监听器类型
	 */
	@Query(type = QueryType.EQ)
	private String listenerType;
	/**
	 * 事件
	 */
	private String event;
	/**
	 * 值类型
	 */
	private String valueType;
	/**
	 * 值
	 */
	private String value;

	public Listener() {
		super();
	}

	public Listener(String id){
		super(id);
	}


}
