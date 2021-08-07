package com.resta.springboot.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
@Entity
@Table(name = "Employee")
public class Employee {
	@Id 
	@Column(name = "EmployeeID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeID; 
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Login_ID", nullable =  false)
	private AppUser loginId;
	
	@Column(name = "Name", length = 50, nullable = false)
	private String empName; 
	 
	
	@Column(name = "Gender", length = 10, nullable = false)
	private String empGender;
	 
	@Column(name = "Avatar", nullable = true, columnDefinition = "varchar(max)")
	private String empAvatar;
	
	@Column(name = "Birthday",   nullable = false)
	private Date empBirthday;
}
