package com.ysd.util;

import java.util.HashMap;
import java.util.Map;

public class Json {
	/**
	 *
	 * @param status 状态码
	 * @param msg    消息
	 * @param data   结果集
	 * @return
	 */
	public Map<String, Object> getJson(String status, String msg, Object data) {
		Map<String, Object> json = new HashMap<>();
		json.put("status", status);
		json.put("msg", msg);
		json.put("data", data);
		return json;
	}

	/**
	 *
	 * @param status 状态码
	 * @param msg    消息
	 * @param count  数量
	 * @param data   结果集
	 * @return
	 */
	public Map<String, Object> getJsonWeb(String status, String msg, Integer count, Object data) {
		Map<String, Object> json = new HashMap<>();
		json.put("status", status);
		json.put("msg", msg);
		json.put("count", count);
		json.put("data", data);
		return json;
	}
}