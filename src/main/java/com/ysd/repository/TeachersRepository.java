package com.ysd.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ysd.entity.Teachers;

public interface TeachersRepository extends JpaRepository<Teachers, Integer>,JpaSpecificationExecutor<Teachers>{

	@Transactional
	@Query(value = "select * from teachers INNER JOIN sections on sections.id=teachers.section_id where teachers.card_no=?1",nativeQuery = true)
	Teachers findByCardNo(String cardNo);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE `russ`.`teachers` SET `name` =:#{#teachers.name}, `sex` = :#{#teachers.sex}, `status` = :#{#teachers.status}, `remark` = :#{#teachers.remark} WHERE `card_no` = :#{#teachers.cardNo}",nativeQuery = true)
	Integer saveTeachersByCardNo(@Param("teachers")Teachers teachers);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE from teachers where id=?1",nativeQuery = true)
	Integer deleteByid(Integer id);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO `russ`.`teachers`( `card_no`, `name`, `sex`, `section_id`, `status`, `remark`)"
			+ " VALUES ( :#{#teachers.cardNo}, :#{#teachers.name}, :#{#teachers.sex}, :#{#teachers.sections.id}, :#{#teachers.status}, :#{#teachers.remark})",nativeQuery = true)
	Integer saveTeachers(@Param("teachers")Teachers teachers);
}



