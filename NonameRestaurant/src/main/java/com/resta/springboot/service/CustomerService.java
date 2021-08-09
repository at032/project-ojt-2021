package com.resta.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resta.springboot.model.Customer;
import com.resta.springboot.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository cusRepo;
	
	public List<Customer> getAllCustomer() {
		return cusRepo.findAll();
	}
	
	public List<Customer> getCustomerByClass(String classCus) {
		return cusRepo.findByClassCus(classCus);
	}

	public void saveCustomer(Customer customer) {
		cusRepo.save(customer);
	}

	public void deleteCustomer(int id) {
		cusRepo.deleteById(id);
	}
	public Customer findCusByEmail(String email) {
		Customer customer = new Customer();
		for(Customer cus : getAllCustomer()) {
			if(cus.getLoginId().getEmail().equals(email)) {
				customer = cus;
				return customer;
			}
		}
		return customer;
	}
	public Customer findById(int id) {
		return cusRepo.findByCustomerID(id);
		
	}
//	public boolean checkEmailExist(String email) {
//		for(Customer cus : getAllCustomer()) {
//			if(cus.getLoginId().getEmail().equals(email)) {
//				return false;
//			}
//		}
//		return true;
//	}
}
