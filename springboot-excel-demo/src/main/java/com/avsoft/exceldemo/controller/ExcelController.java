package com.avsoft.exceldemo.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avsoft.exceldemo.model.Employee;
import com.avsoft.exceldemo.service.ExcelService;

@RestController
public class ExcelController {

	@Autowired
	ExcelService excelService;

	@GetMapping("/excel")
	public ResponseEntity<byte[]> downloadExcel() throws IOException {
		List<Employee> employees = Arrays.asList(new Employee("John Doe", "john.doe@example.com", "HR"),
				new Employee("Jane Smith", "jane.smith@example.com", "Finance"),
				new Employee("Jack Johnson", "jack.johnson@example.com", "IT"),
				new Employee("Jack1 Johnson", "jack1.johnson@example.com", "IT"),
				new Employee("Jack2 Johnson", "jack2.johnson@example.com", "IT"));
		byte[] excelBytes = excelService.generateExcel(employees);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.xlsx")
				.body(excelBytes);

	}

}
