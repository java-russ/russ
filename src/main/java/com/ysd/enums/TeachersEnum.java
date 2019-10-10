package com.ysd.enums;

import lombok.Getter;

@Getter
public enum TeachersEnum {
	
	STATUS(0,"老师未在任何资源地方"),
	SUCCESS(1,"对老师操作成功的状态"),
	;
	private Integer code;
	private String msg;
	
	private TeachersEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
