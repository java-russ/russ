package com.ysd.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ysd.entity.Memberships;

public interface MembershipsRepository extends JpaRepository<Memberships, Integer> {

	@Query(value = "select distinct department from memberships",nativeQuery = true)
	List<Memberships> findDepartmentAll();
	
	@Query(value = "select * from memberships where specialty=?1",nativeQuery = true)
	Memberships findDepartmentBySpecialty(String specialty);
	
	@Query(value = "select * from memberships",nativeQuery = true)
	List<Memberships> findMembershipsAll();
}
