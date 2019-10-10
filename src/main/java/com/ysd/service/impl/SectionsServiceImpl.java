package com.ysd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysd.entity.Sections;
import com.ysd.repository.SectionsRepository;
import com.ysd.service.SectionsService;

@Service
public class SectionsServiceImpl implements SectionsService {

	@Autowired
	private SectionsRepository sectionsRepository;
	
	@Override
	public List<Sections> findSectionsAll() {
		return sectionsRepository.findAll();
	}

}
