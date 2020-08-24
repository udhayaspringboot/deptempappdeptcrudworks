package com.departmentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.departmentservice.model.Department;
import com.departmentservice.model.Departments;
import com.departmentservice.service.DepartmentService;
@RestController
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	
	@PostMapping("/saveDept")
	public Department saveDept(@RequestBody Department dept)
	{
		
		
		return departmentService.createDeptServ(dept);
		
	}
	
	@GetMapping("/listDept")
	public Departments getAllDept()
	{
		List<Department> lis = departmentService.readAllDeptServ();
		Departments depts = new Departments();
		depts.setDepartments(lis);
		
		return depts;
	}
	
	@PutMapping("/updateDept/{depId}")
	public boolean updateDept(@RequestBody Department dept,@PathVariable int depId)
	{
		System.out.println("dept updating values"+depId + "name "+dept.getDeptName() +"loc "+dept.getDeptLoc());
		dept.setDeptId(depId);
		departmentService.updateDeptServ(dept);
		
		return true;
		
	}
	
	@DeleteMapping("/deleteDept/{depIds}")
	public boolean deleteDept(@PathVariable int depIds)
	{
		departmentService.delteDeptServ(depIds);
		
		return true;
		
	}
	

}
