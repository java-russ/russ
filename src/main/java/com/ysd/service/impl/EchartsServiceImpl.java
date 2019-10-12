package com.ysd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysd.entity.Readrooms;
import com.ysd.repository.ReadroomsRepository;
import com.ysd.repository.StatisticsRepoditory;
import com.ysd.repository.StudentsRepository;
import com.ysd.repository.TeachersRepository;
import com.ysd.service.EchartsService;
import com.ysd.util.InitDateTime;


@Service
public class EchartsServiceImpl implements EchartsService {

	@Autowired
	private StudentsRepository studentsRepository;
	
	@Autowired
	private TeachersRepository teachersRepository;
	
	@Autowired 
	private ReadroomsRepository readroomsRepository;
	
	@Autowired
	private StatisticsRepoditory statisticsRepoditory;
	
	@Override
	public List<Map<String, Object>> selectSpecialtyStudentEcharts(String beginTime, String endTime, String rname) {
		return studentsRepository.selectEcharts(beginTime, endTime, rname);
	}

	@Override
	public List<Map<String, Object>> selectSnameTeacherEcharts(String beginTime, String endTime, String rname) {
		return teachersRepository.selectEcharts(beginTime, endTime, rname);
	}

	@Override
	public List<String[]> selectStudentReadromsEcharts(String years, String months, String days) {
		List<String[]> lisArray=new ArrayList<String[]>();
		List<Readrooms>  readroomsList = readroomsRepository.findAll();
		String[] str=new String[readroomsList.size()+1];
		//
		str[0]="readroomsList";
		for(int i=0;i<readroomsList.size();i++) {
			str[i+1]=readroomsList.get(i).getRname();
		}
		lisArray.add(str);
		
		List<Map<String,Object>> tadayshuLiang = statisticsRepoditory.findStatisNumShuLiang(InitDateTime.getYear(), InitDateTime.getMounth(), InitDateTime.getDay());
		List<Map<String,Object>> zuotianshuLiang = statisticsRepoditory.findStatisNumShuLiang(InitDateTime.getYear(), InitDateTime.getMounth(), InitDateTime.getZuoTianDay());
		
		String[] pro=new String[zuotianshuLiang.size()+1];
		pro[0]=years+"-"+months+"-"+days;
		for(int i=0;i<tadayshuLiang.size();i++) {
			Integer tadaynum=Integer.valueOf(tadayshuLiang.get(i).get("prople_nums").toString());
			Integer zuotiannum=Integer.valueOf(zuotianshuLiang.get(i).get("prople_nums").toString());
			Integer prople_nums=tadaynum-zuotiannum;
			pro[i+1]=prople_nums.toString();
		}
		lisArray.add(pro);
		return lisArray;
	}

}
