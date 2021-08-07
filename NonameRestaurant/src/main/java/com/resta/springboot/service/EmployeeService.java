package com.resta.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resta.springboot.model.Employee;
import com.resta.springboot.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository empRepo;
	
	public List<Employee> getAllEmployee() {
		return empRepo.findAll();
	}
	
	public void saveEmployee(Employee employee) {
		empRepo.save(employee);
	}

	public void deleteEmployee(int id) {
		empRepo.deleteById(id);
	}
	public Employee findEmpByEmail(String email) {
		Employee employee = new Employee();
		for(Employee emp : getAllEmployee()) {
			if(emp.getLoginId().getEmail().equals(email)) {
				employee = emp;
				return employee;
			}
		}
		return employee;
	}
	public Employee findByLoginId(long id) {
		return empRepo.findByLoginId(id);
		
	}
}
