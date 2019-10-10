package com.ysd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysd.entity.Memberships;
import com.ysd.service.MembershipsService;

@RestController
public class MembershipsController {

	@Autowired
	private MembershipsService membershipsService;
	@GetMapping("/findMembershipsAll")
	public List<Memberships> findMembershipsAll(){
		return membershipsService.findMembershipsAll();
	}
}
