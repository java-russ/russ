package com.ysd.service.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ysd.entity.Sections;
import com.ysd.entity.Teachers;
import com.ysd.enums.ResultEnum;
import com.ysd.enums.TeachersEnum;
import com.ysd.exception.SellException;
import com.ysd.form.TeacherForm;
import com.ysd.repository.SectionsRepository;
import com.ysd.repository.TeachersRepository;
import com.ysd.service.TeacherService;
import com.ysd.util.Json;
import com.ysd.util.KeyUtil;
import com.ysd.util.PageUtil;

@Service
public class TeacherServiceImpl implements TeacherService {
	
	@Autowired
	private TeachersRepository teachersRepository;

	@Autowired
	private SectionsRepository sectionsRepository;
	
	@Override
	public PageUtil findTeacherAll(TeacherForm teacherForm, Integer page, Integer rows) {
		Sort sort=new Sort(Sort.Direction.DESC,"id");
		Pageable pageable=PageRequest.of(page-1, rows,sort);
		Page<Teachers> page2 = teachersRepository.findAll(getSpecification(teacherForm),pageable);
		return new PageUtil(page2);
	}
	
	private Specification<Teachers> getSpecification(TeacherForm teacherForm){
		return new Specification<Teachers>() {
			@Override
			public Predicate toPredicate(Root<Teachers> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate =criteriaBuilder.conjunction();
				List<Expression<Boolean>> list = predicate.getExpressions();
				if(teacherForm.getCardNo()!=null && !"".equals(teacherForm.getCardNo().toString())) {
					list.add(criteriaBuilder.equal(root.<String>get("cardNo"), teacherForm.getCardNo()));
				}
				if(teacherForm.getName()!=null && !"".equals(teacherForm.getName())) {
					list.add(criteriaBuilder.like(root.get("name"), "%"+teacherForm.getName()+"%"));
				}
				if(teacherForm.getSex()!=null && !"".equals(teacherForm.getSex())) {
					list.add(criteriaBuilder.equal(root.get("sex"), teacherForm.getSex()));
				}
				Join<Sections, Teachers> join=root.join("sections",JoinType.INNER);
				
				if(teacherForm.getSname()!=null && !"".equals(teacherForm.getSname())) {
					list.add(criteriaBuilder.equal(join.<String>get("id"), teacherForm.getSname()));
				}
				return predicate;
			}
		};
	}

	@Override
	public Map<String, Object> insertAndEdit(TeacherForm teacherForm) {
		String msg="修改成功";
		if(teacherForm.getId()==null) {
			msg="添加成功";
			teacherForm.setCardNo(KeyUtil.getTeacherCardNo());
		}
		 Sections sections = sectionsRepository.findById(teacherForm.getSectionId()).get();
		
		Teachers teachers =new Teachers();
		BeanUtils.copyProperties(teacherForm, teachers);
		
		teachers.setSections(sections);
		
		Teachers result = teachersRepository.save(teachers);
		if(result==null) {
			throw new SellException(ResultEnum.INSERT_TEACHER_OR_UPDATE_TEACHER_ERROR);
		}
		return new Json().getJson(TeachersEnum.SUCCESS.getCode().toString(), msg, result);
	}

	@Override
	public Teachers findTeachersById(Integer id) {
		return teachersRepository.findById(id).get();
	}

	@Override
	public Map<String, Object> deleteByid(Integer id) {
		Integer result = teachersRepository.deleteByid(id);
		String msg= result>0 ? "删除成功":"删除失败";
		return new Json().getJson("", msg, result);
	}

	@Override
	public Integer saveTeachers(List<Teachers> teachers) {
		Integer result=0;
		for (Teachers teachers2 : teachers) {
			teachers2.setCardNo(KeyUtil.getTeacherCardNo());
			teachers2.setStatus(TeachersEnum.STATUS.getCode().toString());
			result+=teachersRepository.saveTeachers(teachers2);
		}
		return result;
	}

}
