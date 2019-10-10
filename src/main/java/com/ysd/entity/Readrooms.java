package com.ysd.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Readrooms {

	@Id
	@GeneratedValue
	private Integer id;//资源编号
	private String rname;//阅览室名称
	private String remark;//备注
	
	
}
