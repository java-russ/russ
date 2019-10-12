package com.ysd.service;

import com.ysd.entity.Statistics;

public interface StatisticsService {
	
	/**
	 * 查询当前Statistics是否存在
	 * @param statistics
	 * @return
	 */
	Statistics findStatistics(Statistics statistics);
	
	/**
	 * 添加Statistics
	 * @param statistics
	 * @return
	 */
	Integer saveStatistics(Statistics statistics);
}
