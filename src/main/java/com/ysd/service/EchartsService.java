package com.ysd.service;

import java.util.List;
import java.util.Map;

public interface EchartsService {
	/**
	 * 查询专业的学生在某个时间范围去的某个资源的次数
	 * @param beginTime  开始时间
	 * @param endTime  结束时间
	 * @param rname  资源名 （图书馆、电子阅览室。。等）
	 * @return 返回
	 */
	List<Map<String, Object>> selectSpecialtyStudentEcharts(String beginTime,String endTime,String rname);
	
	/**
	 * 查询科室的老师在某个时间范围去的某个资源的次数
	 * @param beginTime  开始时间
	 * @param endTime  结束时间
	 * @param rname  资源名 （图书馆、电子阅览室。。等）
	 * @return 返回
	 */
	List<Map<String, Object>> selectSnameTeacherEcharts(String beginTime,String endTime,String rname);
	
	/**
	 * 查询学生去资源地的统计次数的柱状统计图
	 * @param years 
	 * @param months
	 * @param days
	 * @return
	 */
	List<String []> selectStudentReadromsEcharts(String years,String months,String days);
}
