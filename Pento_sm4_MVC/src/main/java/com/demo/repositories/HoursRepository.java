package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Account;
import com.demo.entities.Hours;
import com.demo.entities.Tables;

public interface HoursRepository extends CrudRepository<Hours, Integer>{
	
}
