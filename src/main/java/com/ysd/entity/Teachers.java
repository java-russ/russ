package com.ysd.entity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Teachers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//编号
	private String cardNo;//卡号
	private String name;//姓名
	private String sex;//性别
	//private Integer sectionId;//科室编号
	private String status;//0：正常>0：所在的阅览室ID
	private String remark;//备注
	
	@JoinColumn(name = "sectionId",referencedColumnName = "id")
	@ManyToOne(targetEntity = Sections.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Sections sections;
}
