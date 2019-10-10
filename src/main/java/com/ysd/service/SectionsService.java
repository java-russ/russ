package com.ysd.service;

import java.util.List;

import com.ysd.entity.Sections;

public interface SectionsService {
	
	/**
	 * 查询全部的科室作为下拉列表使用
	 * @return
	 */
	List<Sections> findSectionsAll();
}
