package com.ysd.repository;



import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ysd.entity.Students;

/**
 * @author SPZ
 * 2019年9月29日
 */
public interface StudentsRepository extends JpaRepository<Students, Integer>,JpaSpecificationExecutor<Students> {

	@Transactional
	@Query(value = "SELECT * FROM students INNER JOIN memberships on students.menbershipsID=memberships.mid where students.card_no=?1",nativeQuery = true)
	Students findByCardNo(String cardNo);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE `russ`.`students` SET `name` =:#{#students.name}, `sex` =:#{#students.sex}, `status` = :#{#students.status}, `remark` = :#{#students.remark}, `stu_no` = :#{#students.stuNo} WHERE `card_no` = :#{#students.cardNo}",nativeQuery = true)
	Integer saveStudentByCardNo(@Param("students")Students students);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE from students where id=?1",nativeQuery = true)
	Integer deleteByid(Integer id);
	
	@Query(value = "SELECT * FROM students where stu_no=?1",nativeQuery = true)
	Students findStudentByStuNo(String stuNo);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO `russ`.`students`(`card_no`, `name`, `sex`, `menbershipsid`, `status`, `remark`, `stu_no`) VALUES "
			+ "( :#{#students.cardNo}, :#{#students.name}, :#{#students.sex}, (SELECT mid FROM memberships where specialty=:#{#students.memberships.specialty}), :#{#students.status}, :#{#students.remark},:#{#students.stuNo})",nativeQuery = true)
	Integer saveStudent(@Param("students")Students students);
	
	@Query(value = "SELECT memberships.specialty as `name`, count( students.id ) as `value` FROM memberships INNER JOIN students ON memberships.mid = students.menbershipsid INNER JOIN consumelogs ON students.card_no = consumelogs.card_no INNER JOIN readrooms ON consumelogs.read_roomsid = readrooms.id WHERE consumelogs.in_time BETWEEN ?1  AND ?2 AND readrooms.rname = ?3 GROUP BY memberships.specialty",nativeQuery = true)
	List<Map<String, Object>> selectEcharts(String beginTime,String endTime,String rname);
}
