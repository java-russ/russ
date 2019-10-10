package com.ysd.service;

import java.util.List;

import com.ysd.entity.Memberships;

public interface MembershipsService {

	/**
	 * 查询全部的专业作为下拉列表使用
	 * @return
	 */
	List<Memberships> findMembershipsAll();
}
