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
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ysd.entity.Memberships;
import com.ysd.entity.Students;
import com.ysd.enums.ResultEnum;
import com.ysd.enums.StudentsEnum;
import com.ysd.exception.SellException;
import com.ysd.form.StudentForm;
import com.ysd.repository.MembershipsRepository;
import com.ysd.repository.StudentsRepository;
import com.ysd.service.StudentService;
import com.ysd.util.Json;
import com.ysd.util.KeyUtil;
import com.ysd.util.PageUtil;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentsRepository studentsRepository;
	
	@Autowired
	private MembershipsRepository membershipsRepository;
	

	@Override
	public PageUtil findStudentAll(StudentForm studentForm, Integer page, Integer rows) {
		Sort sort=new Sort(Sort.Direction.DESC,"id");
		Pageable pageable=PageRequest.of(page-1,rows,sort);
		Page<Students> page2 = studentsRepository.findAll(getSpecification(studentForm), pageable);
		return new PageUtil(page2);
	}
	
	 /**
	 * 查询条件动态组装
     * 动态生成where语句
     * 匿名内部类形式
     * @param studentForm
     * @return
     */
	private Specification<Students> getSpecification(StudentForm studentForm){
		return new Specification<Students>() {
			
			@Override
			public Predicate toPredicate(Root<Students> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate=criteriaBuilder.conjunction();
				//动态sql表达式集合
				List<Expression<Boolean>> list = predicate.getExpressions();
				
				if(studentForm.getCardNo()!=null && !"".equals(studentForm.getCardNo())) {
					list.add(criteriaBuilder.equal(root.<String>get("cardNo"), studentForm.getCardNo()));
				}
				if(studentForm.getName()!=null && !"".equals(studentForm.getName())) {
					list.add(criteriaBuilder.like(root.<String>get("name"), "%"+studentForm.getName()+"%"));
				}
				if(studentForm.getSex()!=null && !"".equals(studentForm.getSex())) {
					list.add(criteriaBuilder.equal(root.<String>get("sex"), studentForm.getSex()));
				}
				//连表
				Join<Memberships, Students> join = root.join("memberships", JoinType.INNER);
				
				if(studentForm.getDepartment()!=null && !"".equals(studentForm.getDepartment())) {
					list.add(criteriaBuilder.equal(join.<String>get("department"), studentForm.getDepartment()));
				}
				
				if(studentForm.getSpecialty()!=null && !"".equals(studentForm.getSpecialty())) {
					list.add(criteriaBuilder.equal(join.<String>get("specialty"), studentForm.getSpecialty()));
				}
				return predicate;	
			}
		};
	}

	@Transactional
	@Override
	public Map<String, Object> insertAndEdit(StudentForm studentForm) {
		String msg="修改成功";
		if(studentForm.getId()==null) {
			msg="添加成功";
			studentForm.setCardNo(KeyUtil.getStudentCardNo());
		}
		Memberships memberships = membershipsRepository.findById(studentForm.getMenbershipsID()).get();
		
		Students students =new Students();
		BeanUtils.copyProperties(studentForm, students);
		
		students.setMemberships(memberships);
		
		Students result = studentsRepository.save(students);
		if(result==null) {
			throw new SellException(ResultEnum.INSERT_STUDENT_OR_UPDATE_STUDENT_ERROR);
		}
		return new Json().getJson(StudentsEnum.SUCCESS.getCode().toString(), msg, result);
	}

	@Override
	public Students findStudentById(Integer id) {
		return studentsRepository.findById(id).get();
	}

	@Override
	public Map<String, Object> deleteByid(Integer id) {
		Integer result = studentsRepository.deleteByid(id);
		String msg= result>0 ? "删除成功":"删除失败";
		return new Json().getJson("", msg, result);
	}

	@Override
	public Students findStudentByStuNo(String stuNo) {
		return studentsRepository.findStudentByStuNo(stuNo);
	}

	@Transactional
	@Override
	public Integer saveStudentAll(List<Students> students) {
		Integer result=0;
		for (Students stu : students) {
			stu.setStatus(StudentsEnum.STATUS.getCode().toString());
			stu.setCardNo(KeyUtil.getStudentCardNo());
			result += studentsRepository.saveStudent(stu);
		}
		return result;
	}

}
