package com.ysd.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysd.service.UsersService;

@RestController
public class UsersController {

	@Autowired
	private UsersService usersService;
	
	@GetMapping("/findUsersByNameAndPwd")
	public Map<String, Object> findUsersByNameAndPwd(String name, String pwd) {
		
		return usersService.findUsersByNameAndPwd(name, pwd);
	}
}
