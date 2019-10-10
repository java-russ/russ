package com.ysd.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Statistics {

	@Id
	@GeneratedValue
	private Integer id;//编号
	private Integer readRoomID;//资源Id
	private Integer propleNums;//使用人次
	private String year;//年
	private String mouth;//月
	private String day;//日
}
