package com.atabur.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.atabur.exceptions.SemesterException;
import com.atabur.exceptions.StudentException;
import com.atabur.model.FirstSemesterMarks;
import com.atabur.model.SecondSemesterMarks;
import com.atabur.model.Student;
import com.atabur.repositories.StudentRepository;

@RestController
public class StudentController {

	@Autowired
	private StudentRepository sRepo;
	
	@PostMapping("/student")
	private Student saveStudent(@RequestBody Student student) {
		return sRepo.save(student);
	}
	
	@GetMapping("/student")
	private List<Student> getStudents(){
		Iterable<Student> iterable = sRepo.findAll();
		List<Student> list = new ArrayList<>();
		iterable.forEach(list :: add);
		return list;
	}
	
	@PostMapping("/student/{roll}/{semester}/{english}/{math}/{science}")
	private Student addMarksOfAStudent(@PathVariable Integer roll, @PathVariable Integer semester, @PathVariable Integer english, @PathVariable Integer math, @PathVariable Integer science) throws SemesterException, StudentException {
		
		Optional<Student> optional = sRepo.findById(roll);
		
		if(optional.isPresent()) {
			if(semester==1) {
				optional.get().setFmarks(new FirstSemesterMarks(english, math, science));
				return sRepo.save(optional.get());
			}
			else if(semester==2) {
				optional.get().setSmarks(new SecondSemesterMarks(english, math, science));
						return sRepo.save(optional.get());
			}
			else throw new SemesterException("Please enter semester no 1/2...!");
		}
		else 
			throw new StudentException("Please enter valid student roll...!");
	}
	
	@GetMapping("/student/averageMarks/{roll}/{semester}")
	private Double getAverageMarks(@PathVariable Integer roll, @PathVariable Integer semester) throws StudentException,SemesterException{
		Optional<Student> optional = sRepo.findById(roll);
		if(optional.isPresent()) {
			if(semester==1) {
				FirstSemesterMarks marks = optional.get().getFmarks();
				Double av = (double)(marks.getEnglish() + marks.getMaths() + marks.getScience())/3;
				return av;
			}
			else if(semester==2) {
				SecondSemesterMarks marks = optional.get().getSmarks();
				Double av = (double)(marks.getEnglish() + marks.getMaths() + marks.getScience())/3;
				return av;
			}
			else throw new SemesterException("Please enter semester no 1/2...!");
		}
		else throw new StudentException("Please enter valid student roll...!");
	}
	
	@GetMapping("/student/top")
	private List<Student> getTopStudents(){
		
		Iterable<Student> iterable = sRepo.findAll();
		
		Map<Student, Integer> map = new HashMap<>();
		
		List<Student> list = new ArrayList<>();
		
		iterable.forEach(s ->{
			FirstSemesterMarks marks1 = s.getFmarks();
			SecondSemesterMarks marks2 = s.getSmarks();
			Integer av = (marks1.getEnglish() + marks1.getMaths() + marks1.getScience() + marks2.getEnglish() + marks2.getMaths() + marks2.getScience())/6;
			map.put(s, av);
			
		});
		int max = Collections.max(map.values());
		
		for (Entry<Student, Integer> entry : map.entrySet()) {
		    if (entry.getValue()==max) {
		        list.add(entry.getKey());
		        map.remove(entry.getKey());
		    }
		}
		
		for (Entry<Student, Integer> entry : map.entrySet()) {
		    if (entry.getValue()==max) {
		        list.add(entry.getKey());
		    }
		}
		return list;
	}
	
}
