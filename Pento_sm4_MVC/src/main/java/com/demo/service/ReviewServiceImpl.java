package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Images;
import com.demo.entities.Reviews;
import com.demo.repositories.ImagesRepository;
import com.demo.repositories.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService{
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Override
	public Iterable<Reviews> findAll() {
		return reviewRepository.findAll();
	}


	@Override
	public boolean save(Reviews reviews) {
		try {
			reviewRepository.save(reviews);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public List<Reviews> findByIdBranch(int id_branch) {
		// TODO Auto-generated method stub
		return reviewRepository.findByIdBranch(id_branch);
	}

	



}
