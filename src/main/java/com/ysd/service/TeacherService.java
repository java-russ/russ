package com.ysd.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.ysd.entity.Teachers;
import com.ysd.form.TeacherForm;
import com.ysd.util.PageUtil;

public interface TeacherService {

	/**
	 * 分页多条件查询Teacher
	 * @param teacherForm
	 * @param page
	 * @param rows
	 * @return
	 */
	PageUtil findTeacherAll(TeacherForm teacherForm,Integer page,Integer rows);
	
	/**
	 * 添加或者修改
	 * @param students
	 * @return
	 */
	Map<String, Object> insertAndEdit(TeacherForm teacherForm);
	
	/**
	 * 根据id去查看
	 * @param id
	 * @return
	 */
	Teachers findTeachersById(Integer id);
	
	/**
	 * 根据id去删除
	 * @param id
	 * @return
	 */
	Map<String, Object> deleteByid(Integer id);
	
	/**
	 * 批量添加
	 * @param teachers
	 * @return
	 */
	Integer saveTeachers(List<Teachers> teachers);
}
