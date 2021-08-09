package com.resta.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resta.springboot.model.AppUser;
import com.resta.springboot.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public List<AppUser> getAllUser() {
		return userRepository.findAll();
		
	}
	public AppUser findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public void saveUser(AppUser user) {
		userRepository.save(user);
	}

	public void deleteCustomer(long id) {
		userRepository.deleteById(id);
	}
	public boolean checkEmailExist(String email) {
		for(AppUser user:getAllUser()) {
			if (user.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	public boolean checkPhoneExist(String phone) {
		for(AppUser user:getAllUser()) {
			if (user.getPhone().equals(phone)) {
				return true;
			} 
		}
		return false;
	}
	public boolean checkPasswordChange(long id,String password) {
		AppUser user = userRepository.findByUserId(id);
		if(user.getEncrytedPassword().equals(password)) {
			return true;
		}
		return false;
	}
	//sair
}
