package com.atabur.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "student")
public class Student {
	
	@Id
	private String role;
	
	private String name;
	
	@Autowired
	private FirstSemesterMarks fmarks;
	
	@Autowired
	private SecondSemesterMarks smarks;
}
