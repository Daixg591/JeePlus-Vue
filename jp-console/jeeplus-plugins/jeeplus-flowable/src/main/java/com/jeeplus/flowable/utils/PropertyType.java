/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.flowable.utils;

import java.util.Date;

/**
 * 属性数据类型
 * @author liugaofeng
 */
public enum PropertyType {

	S(String.class),
	I(Integer.class),
	L(Long.class),
	F(Float.class),
	N(Double.class),
	D(Date.class),
	SD(java.sql.Date.class),
	B(Boolean.class);

	private Class<?> clazz;

	private PropertyType(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getValue() {
		return clazz;
	}
}
