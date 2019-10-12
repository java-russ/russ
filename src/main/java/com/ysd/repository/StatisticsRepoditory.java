package com.ysd.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ysd.entity.Statistics;

public interface StatisticsRepoditory extends JpaRepository<Statistics, Integer> {

	@Query(value = "select * from statistics where read_roomid=:#{#statistics.readrooms.id} and `year`=:#{#statistics.year} and mouth=:#{#statistics.mouth} and day=:#{#statistics.day}",nativeQuery = true)
	Statistics findStatistics(@Param("statistics") Statistics statistics);
	
	@Transactional
	@Modifying
	@Query(value = "update statistics set prople_nums=prople_nums+1 where id=?1",nativeQuery = true)
	Integer setStatisticsAdd(Integer id);
	
	@Query(value = "select IFNULL(max(prople_nums),0) as prople_nums from statistics where read_roomid=?1",nativeQuery = true)
	Map<String, Object> finxMaxStatistics(Integer id);
	
	@Query(value = "select readrooms.rname,statistics.prople_nums from readrooms INNER JOIN statistics ON readrooms.id=statistics.read_roomid where `year`=?1 and mouth = ?2 and `day`=?3",nativeQuery = true)
	List<Map<String, Object>> findStatisNumShuLiang(String years,String mouth,String days);
}
