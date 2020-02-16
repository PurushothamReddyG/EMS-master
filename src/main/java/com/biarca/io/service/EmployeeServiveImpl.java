package com.biarca.io.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biarca.io.model.Employee;
import com.biarca.io.repo.EmployeeDaoImpl;

@Service
public class EmployeeServiveImpl implements EmployeeService {

	@Autowired
	private EmployeeDaoImpl employeeDaoImpl;

	public List<Employee> findAll() {
		return employeeDaoImpl.findAll();
	}

	public boolean existsById(int id) {
		return employeeDaoImpl.existsById(id);
	}

	public void update(Employee employee) {
		employeeDaoImpl.update(employee);

	}

	public Employee findById(Integer id) {
		return employeeDaoImpl.findById(id);
	}

	public void delete(Employee employee) {
		employeeDaoImpl.delete(employee);
		
	}

	public void deleteAll() {
		employeeDaoImpl.deleteAll();
		
	}

	public void save(Employee employee) {
		employeeDaoImpl.save(employee);
		
	}

}
