package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.demo.entities.Contact;
import com.demo.repositories.ContactRepository;




@Service
public class ContactServiceImpl implements ContactService{
	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public Iterable<Contact> findAll() {
		return contactRepository.findAll();
	}

	@Override
	public Contact find(int id) {
		return contactRepository.findById(id).get();
	}

	@Override
	public boolean save(Contact contact) {
		try {
			contactRepository.save(contact);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean edit(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			contactRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
}
