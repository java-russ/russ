package com.ysd.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


@Data
@Entity
public class Users {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uid;
	private String name;
	private String pwd;
	private String protectEmail;
	private String protectMtel;
	private Integer isLockout;
	private String createTime;
	private String lastLoginTime;
	private Integer pwdWrongTime;
	private String lockTime;
}
