package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.demo.entities.Branchs;
import com.demo.entities.Convenient;
import com.demo.entities.Branchs;
import com.demo.repositories.BranchRepository;
import com.demo.repositories.ConvenientRepository;




@Service
public class ConvenientServiceImpl implements ConvenientService{
	@Autowired
	private ConvenientRepository convenientRepository;
	
	@Override
	public Iterable<Convenient> findAll() {
		return convenientRepository.findAll();
	}

	@Override
	public Convenient find(int id) {
		return convenientRepository.findById(id).get();
	}

	@Override
	public boolean save(Convenient convenient) {
		try {
			convenientRepository.save(convenient);
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
			convenientRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
