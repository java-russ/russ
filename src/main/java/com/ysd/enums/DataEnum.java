package com.ysd.enums;

import lombok.Getter;

@Getter
public enum DataEnum {
	
	STUDNENT_BOY("男","学生性别"),
	STUDNENT_GIRL("女","学生性别"),
	
	TEACHER_BOY("男","老师性别"),
	TEACHER_GIRL("女","老师性别"),
	
	;
	
	private String code;
    private String message;
    
	private DataEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
