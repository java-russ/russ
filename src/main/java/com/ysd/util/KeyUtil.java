package com.ysd.util;

import java.util.Random;

public class KeyUtil {
	/**
     * 生成的唯一主键
     * 格式：时间+随机数吧
     * @return
     */
    public static synchronized String getUniqueKey(){
        Random random=new Random();
        Integer number=random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
    
    public static String getStudentCardNo() {
    	String key = getUniqueKey();
    	return "S"+key;
    }
    
    public static String getTeacherCardNo() {
    	String key = getUniqueKey();
    	return "T"+key;
    }

}
