package com.ysd.service;

import java.util.List;
import java.util.Map;

import com.ysd.entity.Readrooms;

public interface PunchCardService {
	/**
	 * 查询全部的资源数据
	 * @return
	 */
	List<Readrooms> findReadroomsAll();
	/**
	 * 判断卡号是否存在
	 * @param cardNo可能是学生的卡号或者老师的卡号
	 * @return 查到把结果返回 查不到返回null
	 */
	Map<String, Object> findByCardNo(String cardNo);
	
	/**
	 * 对传的卡号执行打卡
	 * @param rid 资源id,cardNo可能是学生的卡号或者老师的卡号
	 * @return 打卡成功返回状态码
	 */
	Map<String, Object> saveByCardNo(Integer rid,String cardNo);
}
