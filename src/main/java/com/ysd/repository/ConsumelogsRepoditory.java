package com.ysd.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ysd.entity.Consumelogs;

public interface ConsumelogsRepoditory extends JpaRepository<Consumelogs, Integer> {

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO `russ`.`consumelogs`( `card_no`, `read_roomsid`, `in_time`, `out_time`, `status`) "
			+ "VALUES (:#{#consumelogs.cardNo}, :#{#consumelogs.readRoomsID}, :#{#consumelogs.inTime}, :#{#consumelogs.outTime}, :#{#consumelogs.status})",nativeQuery = true)
	Integer saveConsumelogs(@Param("consumelogs")Consumelogs consumelogs);

	@Transactional
	@Modifying
	@Query(value = "UPDATE consumelogs set out_time= ?1 where id=?2",nativeQuery = true)
	Integer saveConsumelogsByOutTime(String outTime,Integer id);
	
	@Query(value = "select * from consumelogs where card_no=?1 and status=?2",nativeQuery = true)
	Consumelogs findConsumelogsByCardNo(String cardNo,Integer status);
	
	@Transactional
	@Modifying
	@Query(value = "update consumelogs set status=?2 where card_no=?1",nativeQuery = true)
	Integer saveConsumelogsByCardNoUpdateStatus(String cardNo,Integer status);
	
}
