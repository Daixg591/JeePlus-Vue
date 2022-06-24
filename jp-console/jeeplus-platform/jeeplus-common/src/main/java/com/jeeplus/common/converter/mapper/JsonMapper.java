/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.common.converter.mapper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import net.sf.json.JSONNull;

import java.io.IOException;
import java.util.TimeZone;

/**
 * JSONMapper
 * 定制返回给前台的数据格式
 * @699a558eb492484cb80261f1a5872235
 */
public class JsonMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;


	public JsonMapper() {
		this.setSerializationInclusion(Include.NON_NULL);
		SimpleModule netSfJsonModule = new SimpleModule("net.sf.json");
		netSfJsonModule.addSerializer(JSONNull.class, NullSerializer.instance);
		this.registerModule(netSfJsonModule);
		this.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		this.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){
			@Override
			public void serialize(Object value, JsonGenerator jsonGenerator,
					SerializerProvider provider) throws IOException{
				jsonGenerator.writeString("");
			}

        });
		// 进行HTML解码。
		this.registerModule(new SimpleModule().addSerializer(String.class, new JsonSerializer<String>(){
			@Override
			public void serialize(String value, JsonGenerator jsonGenerator,
					SerializerProvider provider) throws IOException{
				//xss攻击避免
				jsonGenerator.writeString(value);
			}
        }));
		// 时间格式化
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		this.setDateFormat(new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss")); 全局配置不启用，请在前台进行格式化。如果启用，导致保存工作流模型时出现bug

		// 设置时区
		this.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
	}


}
