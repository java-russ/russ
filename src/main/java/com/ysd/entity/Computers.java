package com.ysd.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Computers {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;//电脑名称
	private String ip;//电脑IP地址
	private Integer readRoomID;//阅览室
	private String remark;//备注
}
