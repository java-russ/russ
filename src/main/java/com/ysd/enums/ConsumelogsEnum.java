package com.ysd.enums;

import lombok.Getter;

@Getter
public enum ConsumelogsEnum {

	STATIC_FALSE(0,"不在"),
	STTAIC_TRUE(1,"在"),
	;
	
	private Integer code;
	private String msg;
	
	private ConsumelogsEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
