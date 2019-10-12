package com.ysd.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysd.entity.Users;
import com.ysd.enums.DataEnum;
import com.ysd.repository.UsersRepository;
import com.ysd.service.UsersService;
import com.ysd.util.Json;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public Map<String, Object> findUsersByNameAndPwd(String name, String pwd) {
		 Users users = usersRepository.findByUsers(name, pwd);
		 
		 String msg= users !=null ? "登陆成功" : "用户名或密码错误"; 
		 String status= users !=null ? DataEnum.SUCCESS_STATUS.getCode() : DataEnum.ERROR_STATUS.getCode();
		return new Json().getJson( status, msg, users);
	}

}
