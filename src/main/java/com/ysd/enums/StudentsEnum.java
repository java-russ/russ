package com.ysd.enums;

import lombok.Getter;

@Getter
public enum StudentsEnum {
	
	STATUS(0,"学生未在任何资源地方"),
	SUCCESS(1,"对学生的成功操作"),
	;
	private Integer code;
	private String msg;
	
	private StudentsEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
