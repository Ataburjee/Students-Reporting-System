package com.atabur.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirstSemesterMarks {
	
	final Integer semesterNo = 1;

	private Integer English;
	private Integer Maths;
	private Integer Science;
	
}
