package com.ysd.form;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class StudentForm {

	private Integer id;
	@NotEmpty(message = "卡号必须填")
	private String cardNo;//卡号
	private String name;//姓名
	private String sex;//性别
	private Integer menbershipsID;//身份编号
	private String status;//0:正常状态：0当前所在阅览室
	private String remark;//备注
	private String stuNo;//学号 
	
	private Integer mid;
    private String	department;//系
    private String specialty;//专业
}
