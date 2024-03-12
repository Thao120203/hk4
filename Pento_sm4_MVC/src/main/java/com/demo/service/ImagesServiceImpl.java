package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Images;
import com.demo.repositories.ImagesRepository;

@Service
public class ImagesServiceImpl implements ImagesService{
	@Autowired
	private ImagesRepository ImagesRepository;
	
	@Override
	public Iterable<Images> findAll() {
		return ImagesRepository.findAll();
	}

	@Override
	public Images find(int id) {
		return ImagesRepository.findById(id).get();
	}

	@Override
	public boolean save(Images Images) {
		try {
			ImagesRepository.save(Images);
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
			ImagesRepository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



}
