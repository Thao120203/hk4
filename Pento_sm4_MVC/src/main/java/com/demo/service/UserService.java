package com.demo.service;

import com.demo.entities.User;

public interface UserService {

	public Iterable<User> findAll();
	public User find(int id);
	
	// CRUD
	public boolean save(User user);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}
