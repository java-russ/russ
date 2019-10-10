package com.ysd.util;

import org.springframework.web.multipart.MultipartFile;

import com.ysd.enums.DataEnum;

import net.sf.json.JSONObject;

public class ImportExcel {

	public JSONObject addBatchDevice(MultipartFile file) throws Exception{
		JSONObject json = new JSONObject();
		
		return json;
	}
	
	public static boolean boyORgirl(String str) {
		if(str.equals(DataEnum.TEACHER_BOY.getCode())) {
			return true;
		}
		if(str.equals(DataEnum.TEACHER_GIRL.getCode())) {
			return true;
		}
		return false;
	}
	
}
