package com.ysd.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ysd.entity.Readrooms;
import com.ysd.service.PunchCardService;


@Controller
public class PunchCardController {
	
	@Autowired
	private PunchCardService punchCardService;
	
	@GetMapping("/daka")
	@ResponseBody
	public Map<String, Object> findByCardNo(String cardNo) {//学生或者老师输入卡号打卡
		return punchCardService.findByCardNo(cardNo);
	}
	
	@GetMapping("/findAllReadrooms")
	@ResponseBody
	public List<Readrooms> findAllReadrooms(){//查所有的资源id和名称
		return punchCardService.findReadroomsAll();
	}
	
	@PostMapping("/saveByCardNo")
	@ResponseBody
	public Map<String, Object> saveByCardNo(Integer rid,String cardNo){//打卡进入
		return punchCardService.saveByCardNo(rid, cardNo);
	}
}
