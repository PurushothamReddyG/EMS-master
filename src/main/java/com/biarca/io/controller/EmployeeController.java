package com.biarca.io.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biarca.io.model.Employee;
import com.biarca.io.repo.EmployeeDao;
import com.biarca.io.repo.EmployeeDaoImpl;
import com.biarca.io.service.EmployeeServiveImpl;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeServiveImpl employeeServiveImpl;
	public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	 public String listEmployee(Model model) {
		List<Employee> employees = employeeServiveImpl.findAll();
		model.addAttribute("employees", employees);
		return "employee-list";
	    }
	
	@RequestMapping(value = "/newEmployee", method = RequestMethod.GET)
    public String newEmployee(Model model) {
      model.addAttribute("employee", new Employee());
        return "addemployee";
    }

	// -------------------Create a User-----------------------
	@RequestMapping(value = "/add", method = RequestMethod.POST) 
	public String addEmployee(@ModelAttribute("employee") Employee employee,Model model) {
		
		if(employeeServiveImpl.existsById(employee.getId())){
			model.addAttribute("error", "Employee with id "+employee.getId()+" updated successfully...");
			updateEmployee(employee.getId(),employee);
			return "redirect:/";
		}else{
			employeeServiveImpl.save(employee);
			model.addAttribute("error", "Employee with id "+employee.getId()+" saved successfully...");
			logger.info("employee saved successfully...");
			return "redirect:/";
		}
		}
		
		
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editEmployee(@PathVariable("id") Integer id, Model model){
		Employee employee2 = employeeServiveImpl.findById(id);
    	model.addAttribute("employee", employee2);
        return "editemployee";
    }	
	// ------------------- Update a User ---------------

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public String updateEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {
		logger.info("Updating User with id {}", id);

		Employee employee2 = employeeServiveImpl.findById(id);

		employee2.setId(employee.getId());
		employee2.setName(employee.getName());
		employee2.setSalary(employee.getSalary());
		
		employeeServiveImpl.update(employee2);
		return "redirect:/";
	}

	// -------------------Retrieve Single User-----------------------

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getEmployee(@PathVariable("id") Integer id) {
		logger.info("Fetching User with id {}", id);
		Employee employee = employeeServiveImpl.findById(id);
		if (employee == null) {
			logger.error("User with id {} not found.", id);
			return new ResponseEntity<Employee>(employee ,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);

	}
	
	// -------------------Retrieve All Users-------------------------

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String listAllEmployees(Model model) {
		List<Employee> employees = (List<Employee>) employeeServiveImpl.findAll();
		if (employees.isEmpty()) {
			return "employee-list";
		}
		model.addAttribute("employees", employees);
		return "employee-list";
	}
	
	
	// ------------------- Delete a User--------------------------------

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable("id") Integer id,Model model) {
		Employee employee = employeeServiveImpl.findById(id);
		employeeServiveImpl.delete(employee);
		/*List<Employee> employees = (List<Employee>) employeeService.findAll();
		model.addAttribute("employees", employees);*/
		return "redirect:/";
	}

	// ------------------- Delete All Users-----------------------------

	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	public ResponseEntity<Employee> deleteAll() {
		logger.info("Deleting All Users");

		employeeServiveImpl.deleteAll();
		return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
	}
}
