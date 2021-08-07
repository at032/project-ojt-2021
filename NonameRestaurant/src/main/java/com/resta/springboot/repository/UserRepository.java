package com.resta.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 

import com.resta.springboot.model.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long>{
	
	AppUser findByUserId(long userId);

	AppUser findByPhone(String phone); 

	AppUser findByEmail(String email);
	 

//	@Transactional
//	@Modifying
//	@Query(value = "DELETE FROM DEAL WHERE employeeid = :employeeid DELETE FROM EMPLOYEE WHERE employeeid = :employeeid", nativeQuery = true)
//	void deleteByMaNV(@Param("employeeid") String employeeid);

	 
}