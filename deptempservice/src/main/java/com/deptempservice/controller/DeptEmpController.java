package com.deptempservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.deptempservice.model.Department;
import com.deptempservice.model.Departments;

@RestController
public class DeptEmpController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/listDept")
	
	public ModelAndView getDeptVals(HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession sessd = request.getSession();
		
		
		
		Departments departments = restTemplate.getForObject("http://department-service/listDept", Departments.class);
		System.out.println("departments "+departments.getDepartments().get(0).getDeptName());
		List<Department> lisDep = new ArrayList<>();
		for(int i=0;i<departments.getDepartments().size();i++)
		{
			lisDep.add(departments.getDepartments().get(i));
		}
		
		sessd.setAttribute("lisDept", lisDep);
		ModelAndView mdv = new ModelAndView("first");
		mdv.addObject("lisDept", lisDep);
		
		
		return mdv;
		
	}
	
	@RequestMapping("/homeserv")
	public ModelAndView homePage(@ModelAttribute("deptpage") Department det,HttpServletRequest request,HttpServletResponse response,/*Pageable pageable*/@RequestParam(required = false) Integer page)
	{
		
		HttpSession sess = request.getSession();
		Departments departments = restTemplate.getForObject("http://department-service/listDept", Departments.class);
		System.out.println("departments "+departments.getDepartments().get(0).getDeptName());
		List<Department> ldeptj = new ArrayList<>();
		for(int i=0;i<departments.getDepartments().size();i++)
		{
			ldeptj.add(departments.getDepartments().get(i));
		}
		
		
		int size = ldeptj.size();
		
       ModelAndView mdc = new ModelAndView("home3");
       
      
		
		
		sess.setAttribute("ldeptj", ldeptj);
		
		
		mdc.addObject("size", size);
		mdc.addObject("deptlv", ldeptj);
		mdc.addObject("hoser", "hseval");
		return mdc;	
		
		
		
	}
	
	@RequestMapping("/regDept")
	public ModelAndView addDepartment(@ModelAttribute("deptpage") Department dept,HttpServletRequest request, HttpServletResponse response,/*Pageable pageable*/@RequestParam(required = false) Integer page) throws ServletException, IOException {
		
		
		HttpSession sed = request.getSession();
		
		List<Department> ldepty = (List<Department>) sed.getAttribute("ldeptj");
		
		ModelAndView mvn = new ModelAndView("home3");
		
		  
		  mvn.addObject("loggedInUser",
		  sed.getAttribute("loggedInUser")); mvn.addObject("adddept", "regdept");
		 
		mvn.addObject("deptlv", ldepty);
		mvn.addObject("hoser", "hseval");
		request.setAttribute("deptva", 0);
		return mvn;
		
	}
	
	@PostMapping("/savedept")
	public ModelAndView saveDept(@Valid@ModelAttribute("deptpage") Department dept,BindingResult errors,HttpServletRequest request, HttpServletResponse response) {
		HttpSession sez = request.getSession();
		if(errors.hasErrors())
		{
			ModelAndView mvs = new ModelAndView("home3");
			mvs.addObject("loggedInUser", sez.getAttribute("loggedInUser"));
			mvs.addObject("adddept", "regdept");
			mvs.addObject("deptlv", sez.getAttribute("ldeptj"));
			mvs.addObject("hoser", "hseval");
			return mvs;
		}else
		{
		
		
		Department bool =	restTemplate.postForObject("http://department-service/saveDept", dept, Department.class);
		HttpSession sem = request.getSession();
		sem.setAttribute("submitDoneDept","done");
		return new ModelAndView("redirect:homeserv");
		}
	}
	
	@RequestMapping("/editdepartment")
	public ModelAndView editDepartment(@ModelAttribute("deptpage") Department dept,@RequestParam("depId") int deptId,HttpServletRequest request, HttpServletResponse response,@RequestParam(required = false) Integer page) 
	{
		
		
		HttpSession cvb=request.getSession();
		System.out.println("page is "+page);
		List<Department> ldpl = (List<Department>) cvb.getAttribute("ldeptj");
		
		
		ModelAndView mch = new ModelAndView("home3");
		
	      
		mch.addObject("loggedInUser", cvb.getAttribute("loggedInUser"));
		mch.addObject("deptva",deptId);
		mch.addObject("hoser", "hseval");
		mch.addObject("page", page);
		mch.addObject("deptlv", ldpl);
		cvb.setAttribute("sdt", deptId);
		return mch;
		
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/updatedept/{deptId}")
	public ModelAndView updateDepartment(@PathVariable("deptId") int deptId,@ModelAttribute("deptpage") Department dept,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sed = request.getSession();
		List<Department> lDep = (List<Department>) sed.getAttribute("ldeptj");
		
		System.out.println("dept id"+dept.getDeptId() 
		+"name "+dept.getDeptName()+" "+dept.getDeptLoc());
	for (Department department : lDep) {
		if(department.getDeptId() == deptId)
		{
			
	restTemplate.put("http://department-service/updateDept/"+deptId, dept);
			
			HttpSession sel = request.getSession();
			sel.setAttribute("EditDept","done");
		}
	}
		
		
		
		return new ModelAndView("redirect:/homeserv");
		
	}
	
	@RequestMapping("/deledept")
	public ModelAndView deleteDept(@ModelAttribute("deptpage") Department dept,@RequestParam("deptId") int deptId,HttpServletRequest request, HttpServletResponse response) 
	{

		
		System.out.println("deptId for delete at controller"+deptId);
		
		
		restTemplate.delete("http://department-service/deleteDept/"+deptId);
		
		HttpSession sep = request.getSession();
		sep.setAttribute("deleteDoneDept","done");
		return new ModelAndView("redirect:homeserv");
		
	}

}
