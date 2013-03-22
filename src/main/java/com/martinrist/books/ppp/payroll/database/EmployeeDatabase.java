package com.martinrist.books.ppp.payroll.database;

import com.martinrist.books.ppp.payroll.database.exception.DuplicateEmployeeException;
import com.martinrist.books.ppp.payroll.database.exception.EmployeeDatabaseException;
import com.martinrist.books.ppp.payroll.database.exception.InvalidEmployeeException;
import com.martinrist.books.ppp.payroll.model.Employee;

public interface EmployeeDatabase {

	/**
	 * Returns the number of employees currently stored in the database.
	 * 
	 * @return The number of employees in the database.
	 */
	public int getNumberOfEmployees();

	/**
	 * Adds a new employee to the database.
	 * 
	 * @param e The employee object to be added.
	 * @throws IllegalArgumentException if e is null.
	 * @throws InvalidEmployeeException if e has a null or blank employee Id.
	 * @throws DuplicateEmployeeException if the database already contains an employee with the same id.
	 */
	public void addEmployee(Employee e) throws EmployeeDatabaseException;

	/**
	 * Retrieves an employee by id.
	 * 
	 * @param id The id of the employee to be returned.
	 * @return The employee record, or null if no employee with that id is found.
	 */
	public Employee getEmployeeById(String id);

	/**
	 * Removes an employee from the database with a specified id.
	 * 
	 * @param id The id of the employee to be removed.
	 * @return true if the employee has been removed, false if an employee with the specified id was not found.
	 * @throws IllegalArgumentException If id is null or empty.
	 */
	public boolean removeEmployee(String id);
}
