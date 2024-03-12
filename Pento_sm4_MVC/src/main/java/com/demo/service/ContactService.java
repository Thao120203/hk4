package com.demo.service;

import com.demo.entities.Contact;

public interface ContactService {

	public Iterable<Contact> findAll();
	public Contact find(int id);
	
	// CRUD
	public boolean save(Contact contact);
	public boolean edit(int id);
	public boolean delete(int id);

	
}
