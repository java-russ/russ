package com.ysd.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InitDateTime {
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String initTime() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	/**
	 * 获取当前时间后十分钟的时间
	 * @return
	 */
	public static String initTimeAddTenDD() {
		long currentTime = System.currentTimeMillis() + 10 * 60 * 1000;
		Date date = new Date(currentTime);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}
