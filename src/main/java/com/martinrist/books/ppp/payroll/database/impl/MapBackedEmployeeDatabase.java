package com.martinrist.books.ppp.payroll.database.impl;

import java.util.HashMap;
import java.util.Map;

import com.martinrist.books.ppp.payroll.database.EmployeeDatabase;
import com.martinrist.books.ppp.payroll.database.exception.DuplicateEmployeeException;
import com.martinrist.books.ppp.payroll.database.exception.EmployeeDatabaseException;
import com.martinrist.books.ppp.payroll.database.exception.InvalidEmployeeException;
import com.martinrist.books.ppp.payroll.model.Employee;

public class MapBackedEmployeeDatabase implements EmployeeDatabase {

	private final Map<String, Employee> employees = new HashMap<>();

	@Override
	public int getNumberOfEmployees() {
		return employees.size();
	}

	@Override
	public void addEmployee(Employee e) throws EmployeeDatabaseException {

		if (e == null) {
			throw new IllegalArgumentException("Cannot add a null employee record");
		}

		String id = e.getEmployeeId();

		if (id == null || id.isEmpty()) {
			throw new InvalidEmployeeException("Unable to add employee with null or empty employee Id");
		}

		if (employees.containsKey(id)) {
			throw new DuplicateEmployeeException("Database already contains employee with employeeId '" + id + ".");
		}

		// Defensive copy on add to prevent clients editing an Employee by holding onto a reference
		Employee eToAdd = new Employee(id, e.getName(), e.getAddress());
		employees.put(id, eToAdd);
	}

	@Override
	public Employee getEmployeeById(String id) {

		Employee e = employees.get(id);

		if (e == null) {
			return null;
		}

		// Return a defensive copy
		return new Employee(id, e.getName(), e.getAddress());

	}

	@Override
	public boolean removeEmployee(String id) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException("Employee id cannot be null or empty");
		}

		if (getEmployeeById(id) == null) {
			return false;
		}

		employees.remove(id);
		return true;
	}

}
