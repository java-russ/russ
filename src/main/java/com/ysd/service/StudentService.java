package com.ysd.service;


import java.util.List;
import java.util.Map;

import com.ysd.entity.Students;
import com.ysd.form.StudentForm;
import com.ysd.util.PageUtil;

public interface StudentService {
	/**
	 * 分页多条件查询Student
	 * @param studentForm
	 * @param page
	 * @param rows
	 * @return
	 */
	PageUtil findStudentAll(StudentForm studentForm,Integer page,Integer rows);
	
	/**
	 * 添加或者修改
	 * @param students
	 * @return
	 */
	Map<String, Object> insertAndEdit(StudentForm studentForm);
	
	/**
	 * 根据id去查看
	 * @param id
	 * @return
	 */
	Students findStudentById(Integer id);
	
	/**
	 * 根据id去删除
	 * @param id
	 * @return
	 */
	Map<String, Object> deleteByid(Integer id);
	
	/**
	 * 根据学号查询学生防止导入数据时存在重复学生的学号
	 * @param stuNo
	 * @return
	 */
	Students findStudentByStuNo(String stuNo);
	
	/**
	 * 批量添加学生
	 * @param students
	 * @return
	 */
	Integer saveStudentAll(List<Students> students);
	
}
