package com.resta.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
@Entity
@Table(name = "App_User", //
		uniqueConstraints = { //
				@UniqueConstraint(name = "APP_USER_UK", columnNames = "Email") })
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_Id", nullable = false)
	private Long userId; 

	@Column(name = "Email", length = 250, nullable = false)
	private String email;

	@Column(name = "Phone", length = 11, nullable = false)
	private String phone;

	@Column(name = "Encryted_Password", length = 128, nullable = false)
	private String encrytedPassword;

	@Column(name = "Role_name", length = 50, nullable = false)
	private String role;

	@Column(name = "Enabled", length = 1, nullable = false)
	private boolean enabled;
}