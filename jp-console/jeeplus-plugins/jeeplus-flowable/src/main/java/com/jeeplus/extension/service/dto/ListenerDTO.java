/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;


import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 监听器Entity
 * @author 刘高峰
 * @version 2021-10-14
 */


@Data
@EqualsAndHashCode(callSuper = false)
public class ListenerDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 监听器类型
	 */
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

	public ListenerDTO() {
		super();
	}

	public ListenerDTO(String id){
		super(id);
	}


}
