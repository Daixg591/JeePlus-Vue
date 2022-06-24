/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.common.utils;

import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.HashMap;


/**
 * 返回包装wrapper
 * @author
 *
 */
public class ResponseUtil extends HashMap<String,Object> implements Serializable {

	public static ResponseUtil newInstance() {
		ResponseUtil fragment = new ResponseUtil ();
		return fragment;
	}

	public ResponseUtil add (String key, Object value) {
		super.put(key, value);
		return this;
	}

	public ResponseEntity ok() {
		return ResponseEntity.ok (this);
	}

	public ResponseEntity error() {
		return ResponseEntity.badRequest ().body (this);
	}

	public ResponseEntity ok(String msg) {
		this.put ("msg", msg);
		return ResponseEntity.ok (this);
	}

	public ResponseEntity error(String msg) {
		this.put ("msg", msg);
		return ResponseEntity.badRequest ().body (this);
	}


}
