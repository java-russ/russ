package com.ysd.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysd.entity.Consumelogs;
import com.ysd.entity.Readrooms;
import com.ysd.entity.Statistics;
import com.ysd.entity.Students;
import com.ysd.entity.Teachers;
import com.ysd.enums.CardNoEnum;
import com.ysd.enums.ConsumelogsEnum;
import com.ysd.enums.ResultEnum;
import com.ysd.enums.StudentsEnum;
import com.ysd.enums.TeachersEnum;
import com.ysd.exception.SellException;
import com.ysd.repository.ConsumelogsRepoditory;
import com.ysd.repository.ReadroomsRepository;
import com.ysd.repository.StatisticsRepoditory;
import com.ysd.repository.StudentsRepository;
import com.ysd.repository.TeachersRepository;
import com.ysd.service.PunchCardService;
import com.ysd.util.InitDateTime;
import com.ysd.util.Json;

@Service
public class PunchCardServiceImpl implements PunchCardService {
	
	@Autowired
	private StudentsRepository studentsRepository;
	@Autowired
	private TeachersRepository teachersRepository;
	@Autowired
	private ReadroomsRepository readroomsRepository;
	@Autowired
	private ConsumelogsRepoditory consumelogsRepoditory; 
	
	@Autowired
	private StatisticsRepoditory statisticsRepoditory;
	Json json=new Json();
	
	@Override
	public Map<String, Object> findByCardNo(String cardNo) {
		if(cardNoIsExistence(cardNo)) {
			if(isStudent(cardNo)) {
				return JudgingStudentCard(cardNo, json);
			}
			return JudgingTeacherCard(cardNo, json);
		}
		return null;
	}
	
	private Map<String, Object> JudgingTeacherCard(String cardNo, Json json) {
		Teachers teachers = teachersRepository.findByCardNo(cardNo);
		if(teachers==null) {
			return null;
		}
		return json.getJson(ResultEnum.SUCCESS.getCode().toString(), "", teachers);
	}

	private Map<String, Object> JudgingStudentCard(String cardNo, Json json) {
		Students students = studentsRepository.findByCardNo(cardNo);
		if(students==null) {
			return null;
		}
		return json.getJson(ResultEnum.SUCCESS.getCode().toString(), "", students);
	}
	
	@Transactional
	@Override
	public Map<String, Object> saveByCardNo(Integer rid,String cardNo) {
		Readrooms readrooms = readroomsRepository.findById(rid).get();
		if(readrooms==null) {
			throw new SellException(ResultEnum.READROOMS_NOT_EXIST);//资源id不存在
		}
		if(!cardNoIsExistence(cardNo)) {
			throw new SellException(ResultEnum.CARDNO_NOT_EXIST);//卡号不存在
		}
		
		Consumelogs cons = consumelogsRepoditory.findConsumelogsByCardNo(cardNo,ConsumelogsEnum.STTAIC_TRUE.getCode());
		
		if(findByStatus(rid,cardNo)) {//进入则说明当前打卡是离开时打卡
			return saveByCardNoStatus(cons.getId(),cardNo);
		}
		
		
		if(!saveStudentORTeacherStart(rid,cardNo)) {
			throw new SellException(ResultEnum.UPDATE_STUDENT_OR_TEACHER_ERROR);//修改学生或者老师状态失败
		}
		
		//判断当前卡号在Consumelogs中是否有是已‘在’状态（查的是之前是否未打卡离开）TODO 在数据库设置定时执行就可以删除该判断
		
		if(cons!=null) {//如果已在 则进行修改成不在
			consumelogsRepoditory.saveConsumelogsByCardNoUpdateStatus(cardNo,ConsumelogsEnum.STATIC_FALSE.getCode());
		}
		
		Integer consumelogs = consumelogsRepoditory.saveConsumelogs(getConsumelogs(rid, cardNo));
		if(consumelogs<0) {
			throw new SellException(ResultEnum.CONSUMELOGS_SAVE_ERROR);//打卡失败
		}
		saveReadrooms(rid,readrooms);
		return json.getJson(ResultEnum.SUCCESS.getCode().toString(), "成功打卡", consumelogs);
	}
	
	public Integer saveReadrooms(Integer rid,Readrooms readrooms) {
		Statistics statistics = getStatistics(readrooms);
		Statistics statistics2 = statisticsRepoditory.findStatistics(statistics);
		if(statistics2==null) {
			Map<String, Object> map = statisticsRepoditory.finxMaxStatistics(rid);
			statistics.setPropleNums(Integer.valueOf(map.get("prople_nums").toString())+1);
			Statistics save = statisticsRepoditory.save(statistics);
			return save !=null ? 1 : 0;
		}else {
			return statisticsRepoditory.setStatisticsAdd(statistics2.getId());
		}
	}
	
	public Statistics getStatistics(Readrooms readrooms) {
		Statistics statistics=new Statistics();
		statistics.setReadrooms(readrooms);
		statistics.setYear(InitDateTime.getYear());
		statistics.setMouth(InitDateTime.getMounth());
		statistics.setDay(InitDateTime.getDay());
		return statistics;
	}
	
	private Consumelogs getConsumelogs(Integer rid,String cardNo) {
		Consumelogs consumelogs=new Consumelogs();
		consumelogs.setCardNo(cardNo);
		consumelogs.setReadRoomsID(rid);
		consumelogs.setInTime(InitDateTime.initTime());
		consumelogs.setOutTime(InitDateTime.initTimeAddTenDD());
		consumelogs.setStatus(ConsumelogsEnum.STTAIC_TRUE.getCode().toString());
		return consumelogs;
	}
	
	/**
	 * 离开打卡
	 * @param cardNo
	 * @return
	 */
	@Transactional
	public Map<String, Object> saveByCardNoStatus(Integer id,String cardNo){
		Integer result;
		if(isStudent(cardNo)) {
			//学生
			Students students = studentsRepository.findByCardNo(cardNo);
			students.setStatus(StudentsEnum.STATUS.getCode().toString());
			result = studentsRepository.saveStudentByCardNo(students);
		}else {
			//老师
			Teachers teachers = teachersRepository.findByCardNo(cardNo);
			teachers.setStatus(TeachersEnum.STATUS.getCode().toString());
			result = teachersRepository.saveTeachersByCardNo(teachers);
		}
		//修改在consumelogs表的状态和离开时间 TODO
		consumelogsRepoditory.saveConsumelogsByOutTime(InitDateTime.initTime(), id);
		consumelogsRepoditory.saveConsumelogsByCardNoUpdateStatus(cardNo, ConsumelogsEnum.STATIC_FALSE.getCode());
		return json.getJson(ResultEnum.SUCCESS.getCode().toString(), result>0? "离开打卡成功":"离开打卡失败", result);
	}
	public boolean isStudent(String cardNo) {
		String str=cardNo.substring(0, 1);
		if(CardNoEnum.STUDNENT_CARD.getCode().equals(str)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断当前打卡是‘进入’ or ‘离开’
	 * @param rid 资源id 用于在不同的地方连续打卡做判断
	 * @param cardNo 卡号
	 * @return 返回true说明是要打卡离开，返回false说明是要打卡进入
	 */
	public boolean findByStatus(Integer rid,String cardNo){
		if(isStudent(cardNo)) {
			Students cardNo2 = studentsRepository.findByCardNo(cardNo);
			return cardNo2.getStatus().equals(rid.toString()) ? true :false ;
		}else{
			Teachers cardNo2 = teachersRepository.findByCardNo(cardNo);
			return cardNo2.getStatus().equals(rid.toString()) ? true :false ;
		}
	}
	/**
	 * 判断卡号是否存在
	 * @param cardNo
	 * @return
	 */
	public boolean cardNoIsExistence(String cardNo) {
		String str=cardNo.substring(0, 1);
		if(CardNoEnum.STUDNENT_CARD.getCode().equals(str)) {
			Students students = studentsRepository.findByCardNo(cardNo);
			return students==null ? false : true;
		}
		if(CardNoEnum.TEACHER_CARD.getCode().equals(str)){
			Teachers teachers = teachersRepository.findByCardNo(cardNo);
			return teachers==null ? false : true;
		}
		return false;
	}
	
	/**
	 * 是否成功修改老师或者学生的状态
	 * @param rid
	 * @param cardNo
	 * @return
	 */
	@Transactional
	public boolean saveStudentORTeacherStart(Integer rid,String cardNo) {
		if(isStudent(cardNo)){//是学生
			Students students = studentsRepository.findByCardNo(cardNo);
			students.setStatus(rid.toString());
			Integer result = studentsRepository.saveStudentByCardNo(students);
			return result>0 ? true :false;
		}else{//是老师
			Teachers teachers = teachersRepository.findByCardNo(cardNo);
			teachers.setStatus(rid.toString());
			Integer result = teachersRepository.saveTeachersByCardNo(teachers);
			return result>0 ? true :false;
		}
	}
	
	
	
	@Override
	public List<Readrooms> findReadroomsAll() {
		return readroomsRepository.findAll();
	}
}
