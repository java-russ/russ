package com.ysd.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Consumelogs {

	@Id
	@GeneratedValue
	private Integer id;
	private String cardNo;//卡号
	private Integer readRoomsID;//资源编号
	private String inTime;//进入时间
	private String outTime;//离开时间
	private String status;//用于统计而不用于判定0：不在1：表示在
}
