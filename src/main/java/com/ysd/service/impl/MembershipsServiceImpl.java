package com.ysd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysd.entity.Memberships;
import com.ysd.repository.MembershipsRepository;
import com.ysd.service.MembershipsService;

@Service
public class MembershipsServiceImpl implements MembershipsService {

	@Autowired
	private MembershipsRepository membershipsRepository;
	@Override
	public List<Memberships> findMembershipsAll() {
		return membershipsRepository.findAll();
	}

}
