package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.demo.entities.Branchs;
import com.demo.entities.Branchs;
import com.demo.repositories.BranchRepository;




@Service
public class BranchServiceImpl implements BranchService{
	@Autowired
	private BranchRepository branchsRepository;
	
	@Override
	public Iterable<Branchs> findAll() {
		return branchsRepository.findAllNew();
	}

	@Override
	public Branchs find(int id) {
		return branchsRepository.findById(id).get();
	}

	@Override
	public boolean save(Branchs Branchs) {
		try {
			branchsRepository.save(Branchs);
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
			branchsRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Branchs saveid(Branchs Branchs) {
		try {
			
			return branchsRepository.save(Branchs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Branchs> findBranchNamesByEmail(String email) {
		return branchsRepository.findBranchNamesByEmail(email);
	}
}
