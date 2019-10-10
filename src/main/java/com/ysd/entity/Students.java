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
public class Students {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
	private Integer id;//编号
	private String cardNo;//卡号
	private String name;//姓名
	private String sex;//性别
	//private Integer menbershipsid;//身份编号
	private String status;//0:正常状态：0当前所在阅览室
	private String remark;//备注
	private String stuNo;//学号 
	// //name=定义外键在本表的字段名 referencedColumnName =关联外键对象的哪个字段
	@JoinColumn(name = "menbershipsid",referencedColumnName = "mid")
	@ManyToOne(targetEntity = Memberships.class,fetch =FetchType.EAGER,cascade = CascadeType.ALL )
	private Memberships memberships;//学生的系，专业，学历
}
