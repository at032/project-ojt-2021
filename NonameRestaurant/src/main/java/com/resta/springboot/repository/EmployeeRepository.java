package com.resta.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.resta.springboot.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	Employee findByEmployeeID(int id);
	
	Employee findByLoginId(long loginId);
}
