package com.resta.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.resta.springboot.model.AppUser;
import com.resta.springboot.model.MyUserDetails;
import com.resta.springboot.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUser user = this.userRepository.findByEmail(email);
//		return new MyUserDetails(employees);
		if(user != null) {
			
			
			return new MyUserDetails(user);

		}else {
			throw new UsernameNotFoundException("");
		}
	} 
}

