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
@Table(name = "Customer")
//@Table(name = "Customer", //
//		uniqueConstraints = { //
//				@UniqueConstraint(name = "Customer_UK", columnNames = "Email") })

public class Customer {
	@Id 
	@Column(name = "CustomerID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerID; 
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Login_ID", nullable =  false)
	private AppUser loginId;
	
	@Column(name = "Customer_name", length = 50, nullable = false)
	private String cusName; 
	
	@Column(name = "Class", length = 50, nullable = false)
	private String classCus;
	
	@Column(name = "Points",  nullable = false)
	private int points;
	
	@Column(name = "Gender", length = 10, nullable = false)
	private String gender;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Banking_account", nullable = true)
	private BankAccount bankingAccount;
	
	@Column(name = "Avatar", nullable = true, columnDefinition = "varchar(max)")
	private String avatar;
	
	@Column(name = "Birthday",   nullable = false)
	private Date birthday;

	
}
