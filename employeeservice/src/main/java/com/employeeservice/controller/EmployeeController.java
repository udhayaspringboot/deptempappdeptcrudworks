package com.employeeservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employeeservice.model.Employee;
import com.employeeservice.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeService empServ;
	@PostMapping("/saveEmp/{deptEmpFk}")
	public boolean saveEmp(@RequestBody Employee emp,@PathVariable int deptEmpFk)
	{
		emp.setDeptEmpFk(deptEmpFk);
		empServ.createEmpServ(emp);
		
		return true;
	}
	@GetMapping("/listEmp/{deptEmpFk}")
	public List<Employee> getAllEmp(@RequestBody Employee emp,@PathVariable int deptEmpFk)
	{
		List<Employee> lisEmp = empServ.readEmpFromDeptServ(deptEmpFk);
		
		return lisEmp;
	}
	
	@PutMapping("/updateEmp/{empId}")
	public boolean updateEmp(@RequestBody Employee emp,@PathVariable int empId)
	{
		emp.setEmpId(empId);
		empServ.updateEmpServ(emp);
		
		return true;
	}
	
	@DeleteMapping("/deleteEmp/{empId}/{deptEmpFk}")
	public boolean deleteEmp(@RequestBody Employee emp,@PathVariable int empId,@PathVariable int deptEmpFk)
	{
		
		empServ.deleteEmpFromDeptServ(empId, deptEmpFk);
		
		return true;
	}
	

}
