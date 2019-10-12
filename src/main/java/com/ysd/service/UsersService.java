package com.ysd.service;

import java.util.Map;


public interface UsersService {

	/**
	 * 
	 * @param name 前台传的usersName
	 * @param pwd 前台传的pwd
	 * @return 返回User对象
	 */
	Map<String, Object> findUsersByNameAndPwd(String name,String pwd);
}
