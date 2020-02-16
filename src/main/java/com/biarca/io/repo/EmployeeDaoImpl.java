package com.biarca.io.repo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.biarca.io.model.Employee;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	private MongoClient client;

	private MongoDatabase database;
	private MongoCollection<Document> collection;

	@PostConstruct
	public void setup() {
		database = client.getDatabase("ems");
		collection = database.getCollection("employee");
	}

	public List<Employee> findAll() {
		List<Document> list = new ArrayList<Document>();
		List<Employee> empList = new ArrayList<Employee>();
		collection.find().into(list);

		for (Document document : list) {
			Employee employee = new Employee();
			employee.setId(document.getInteger("id"));
			employee.setName(document.getString("name"));
			employee.setSalary(document.getDouble("salary"));
			empList.add(employee);
		}
		return empList;
	}

	public boolean existsById(int id) {
		long result = collection.count(new Document("id", id));
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void update(Employee employee) {
		
		Document filter = new Document("id", employee.getId());
		Document query = new Document();
		query.put("name", employee.getName());
		query.put("salary", employee.getSalary());
		Bson updateOperationDocument = new Document("$set", query);
		collection.updateOne(filter, updateOperationDocument);
	}

	public Employee findById(Integer id) {
		FindIterable<Document> result = collection.find(new Document("id", id));
		Employee employee = new Employee();
		for (Document document : result) {
			employee.setId(document.getInteger("id"));
			employee.setName(document.getString("name"));
			employee.setSalary(document.getDouble("salary"));
		}
		return employee;
	}

	public void delete(Employee employee) {

		collection.deleteOne(new Document("id", employee.getId()));

	}

	public void deleteAll() {
		collection.deleteMany(new Document());

	}

	public void save(Employee employee) {
		Document query = new Document();
		query.put("id", employee.getId());
		query.put("name", employee.getName());
		query.put("salary", employee.getSalary());

		collection.insertOne(query);
		
	}

}
