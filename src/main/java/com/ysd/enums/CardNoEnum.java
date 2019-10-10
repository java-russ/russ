package com.ysd.enums;

import lombok.Getter;

@Getter
public enum CardNoEnum {
	
	STUDNENT_CARD("S","学生卡号"),
	
	TEACHER_CARD("T","老师卡号"),
	
	;
	private String code;
    private String message;
    
	private CardNoEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
    
    
}
