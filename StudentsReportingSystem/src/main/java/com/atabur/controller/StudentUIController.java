package com.atabur.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.atabur.model.FirstSemesterMarks;
import com.atabur.model.SecondSemesterMarks;
import com.atabur.model.Student;
import com.atabur.repositories.StudentRepository;

@Controller
public class StudentUIController {

	@Autowired
	private StudentRepository sRepo;
	
	
	
	@GetMapping("/home")
	public String homeLauncher() {
		
		return "welcome";
	}
	
	
	@GetMapping("/getAllStudents")
	public String getAllStudents(Model model){
	 Iterable<Student> itr= sRepo.findAll();
	 
	 List<Student> students= new ArrayList<>();
	 itr.forEach(students::add);
	 
	 model.addAttribute("studentdata", students);
	 
	 return "allStudents";
	 
	}
	
	@GetMapping("/getAverageMarks")
	public String getAverageMarks(Model model){
	 Iterable<Student> itr= sRepo.findAll();
	 
	 List<Integer> marks= new ArrayList<>();
	 itr.forEach(s ->{;
	 	FirstSemesterMarks marks1 = s.getFmarks();
		SecondSemesterMarks marks2 = s.getSmarks();
		
		Integer av = (marks1.getEnglish() + marks1.getMaths() + marks1.getScience() + marks2.getEnglish() + marks2.getMaths() + marks2.getScience())/6;
		marks.add(av);
	 });
	 
	 model.addAttribute("averagedata", marks);
	 
	 return "average";
	 
	}
	
	@GetMapping("/getTop2Students")
	public String getTopStudents(Model model){
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
	 
	 model.addAttribute("topstudentsdata", list);
	 
	 return "topStudents";
	 
	}
	
}
