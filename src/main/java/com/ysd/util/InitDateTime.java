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
	/**
	 * 获取当前年
	 * @return
	 */
	public static String getYear() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		return sdf.format(new Date());
	}
	/**
	 * 获取当前月
	 * @return
	 */
	public static String getMounth() {
		SimpleDateFormat sdf=new SimpleDateFormat("MM");
		return sdf.format(new Date());
	}
	/**
	 * 获取当前日
	 * @return
	 */
	public static String getDay() {
		SimpleDateFormat sdf=new SimpleDateFormat("dd");
		return sdf.format(new Date());
	}
	
	/**
	 * 获取昨天的天
	 * @return
	 */
	public static String getZuoTianDay() {
		SimpleDateFormat sdf=new SimpleDateFormat("dd");
		return sdf.format(new Date(new Date().getTime() - 24*3600*1000));
	}
}
