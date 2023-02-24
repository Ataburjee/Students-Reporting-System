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
public class SecondSemesterMarks  {
	
	final Integer semesterNo = 2;

	private Integer English;
	private Integer Maths;
	private Integer Science;
	
}
