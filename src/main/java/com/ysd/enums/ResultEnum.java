package com.ysd.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
	SUCCESS(0,"成功状态码"),
	ERROR(1,"失败状态码"),
	CONSUMELOGS_SAVE_ERROR(2,"打卡失败"),
	READROOMS_NOT_EXIST(3,"资源id不存在"),
	CARDNO_NOT_EXIST(4,"卡号不存在"),
	UPDATE_STUDENT_OR_TEACHER_ERROR(5,"修改学生或者老师的status失败"),
	INSERT_STUDENT_OR_UPDATE_STUDENT_ERROR(6,"添加学生或者修改学生失败"),
	INSERT_TEACHER_OR_UPDATE_TEACHER_ERROR(7,"添加老师或者修改老师失败"),
	;
	
	private Integer code;
    private String message;
	private ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
    

}
