package com.ysd.form;

import lombok.Data;

@Data
public class TeacherForm {

	private Integer id;
	private String cardNo;//卡号
	private String name;//姓名
	private String sex;//性别
	private Integer sectionId;//科室编号
	private String status;//0：正常>0：所在的阅览室ID
	private String remark;//备注
	
	private String sname;//科室名称
}
