package com.ysd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysd.entity.Sections;
import com.ysd.service.SectionsService;

@RestController
public class SectionsController {

	@Autowired
	private SectionsService sectionsService;
	
	@GetMapping("/findSectionsAll")
	public List<Sections> findSectionsAll() {
		return sectionsService.findSectionsAll();
	}
}
