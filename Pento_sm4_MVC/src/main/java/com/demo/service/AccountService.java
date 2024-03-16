package com.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.demo.entities.Account;

public interface AccountService extends UserDetailsService{

	public Iterable<Account> findAll();
	public Account find(int id);
	public Account findByEmail(String email);
	
	public boolean findEmail(String email);
	
	// LOGIN
	public Account login(String email, String password);
	// CRUD
	public boolean save(Account account);
	public boolean edit(int id);
	public boolean delete(int id);
	// Find Pasword
	public String findPassword(int id);
	
	
}
