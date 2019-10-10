package com.ysd.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.Data;

@Data
@Entity
public class Memberships {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer mid;
	private String department;//系
	private String specialty;//专业
	private String degree;//学历
}
