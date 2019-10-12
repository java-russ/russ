package com.ysd.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ysd.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	@Query(value="select * from users where name=?1 and pwd=?2",nativeQuery = true)
	Users findByUsers(String name,String pwd);
}
