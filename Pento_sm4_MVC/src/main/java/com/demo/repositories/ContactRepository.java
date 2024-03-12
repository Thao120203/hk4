package com.demo.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Contact;


public interface ContactRepository extends CrudRepository<Contact, Integer>{
	
	@Query("from Contact order by id desc")
	public List<Contact> findAllNew();
	
	@Query("from Contact order by id desc")
	public List<Contact> findIdByEmail();
}
