package com.ysd.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Statistics {

	@Id
	@GeneratedValue
	private Integer id;//编号
	//private Integer readRoomID;//资源Id
	private Integer propleNums;//使用人次
	private String year;//年
	private String mouth;//月
	private String day;//日
	 
	@JoinColumn(name = "readRoomID",referencedColumnName = "id")
	@ManyToOne(targetEntity = Readrooms.class,fetch =FetchType.EAGER,cascade = CascadeType.ALL )
	private Readrooms readrooms;
}
