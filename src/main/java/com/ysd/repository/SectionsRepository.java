package com.ysd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ysd.entity.Sections;

public interface SectionsRepository extends JpaRepository<Sections, Integer> {

	@Query(value = "select * from sections where sname=?1",nativeQuery = true)
	Sections fintSectionsByid(String sname);
}
