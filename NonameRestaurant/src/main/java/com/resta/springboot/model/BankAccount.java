package com.resta.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
@Entity
@Table(name = "Customer_banking")
public class BankAccount {
	@Id 
	@Column(name = "Banking_account", length = 20, nullable = false)
	 private String bankingAccount;  
	
	@Column(name = "Bank_address", length = 100, nullable = false)
	private String bankAddress; 
	
	@Column(name = "Note", nullable = true, columnDefinition = "varchar(max)")
	private String note;
	 
}
