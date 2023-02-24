package com.atabur.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.atabur.model.Student;

@Repository
public interface StudentRepository extends ElasticsearchRepository<Student, Integer>{
	
}