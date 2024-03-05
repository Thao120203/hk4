package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.demo.entities.Branchs;
import com.demo.entities.Branchs;
import com.demo.repositories.BranchRepository;




@Service
public class BranchServiceImpl implements BranchService{
	@Autowired
	private BranchRepository BranchsRepository;
	
	@Override
	public Iterable<Branchs> findAll() {
		return BranchsRepository.findAll();
	}

	@Override
	public Branchs find(int id) {
		return BranchsRepository.findById(id).get();
	}

	@Override
	public boolean save(Branchs Branchs) {
		try {
			BranchsRepository.save(Branchs);
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
			BranchsRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
