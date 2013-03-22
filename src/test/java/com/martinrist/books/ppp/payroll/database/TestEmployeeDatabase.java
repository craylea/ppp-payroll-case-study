package com.martinrist.books.ppp.payroll.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.martinrist.books.ppp.payroll.database.exception.DuplicateEmployeeException;
import com.martinrist.books.ppp.payroll.database.exception.EmployeeDatabaseException;
import com.martinrist.books.ppp.payroll.database.exception.InvalidEmployeeException;
import com.martinrist.books.ppp.payroll.database.impl.MapBackedEmployeeDatabase;
import com.martinrist.books.ppp.payroll.model.Employee;

public class TestEmployeeDatabase {

	private EmployeeDatabase database;

	@Before
	public void setUp() throws Exception {
		database = new MapBackedEmployeeDatabase();
	}

	@Test
	public void testNewDatabaseContainsNoEmployees() {
		assertEquals(0, database.getNumberOfEmployees());
	}

	@Test
	public void testAddEmployeeIncreasesSizeToOne() throws EmployeeDatabaseException {
		Employee e = new Employee("E1", "Martin Rist", "51 St Leonards Rd, Exeter, Devon");
		database.addEmployee(e);
		assertEquals(1, database.getNumberOfEmployees());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullEmployeeThrowsException() throws EmployeeDatabaseException {
		database.addEmployee(null);
	}

	@Test(expected = InvalidEmployeeException.class)
	public void testAddEmployeeWithNullIdThrowsException() throws EmployeeDatabaseException {
		Employee e = new Employee(null, "Martin Rist", "51 St Leonards Rd, Exeter, Devon");
		database.addEmployee(e);
	}

	@Test(expected = InvalidEmployeeException.class)
	public void testAddEmployeeWithEmptyIdThrowsException() throws EmployeeDatabaseException {
		Employee e = new Employee("", "Martin Rist", "51 St Leonards Rd, Exeter, Devon");
		database.addEmployee(e);
	}

	@Test(expected = DuplicateEmployeeException.class)
	public void testAddEmployeeWithDuplicateIdThrowsException() throws EmployeeDatabaseException {
		Employee e1 = new Employee("E1", "Employee 1", "Address 1");
		Employee e2 = new Employee("E1", "Employee 1 - New", "Address 1 - New");
		database.addEmployee(e1);
		database.addEmployee(e2);
	}

	@Test
	public void testAddAndRetrieveEmployeeByIdReturnsEqual() throws EmployeeDatabaseException {
		Employee eAdded = new Employee("E1", "Martin Rist", "51 St Leonards Rd, Exeter, Devon");
		database.addEmployee(eAdded);
		Employee eRetrieved = database.getEmployeeById("E1");
		assertNotNull(eRetrieved);
		assertEquals(eAdded, eRetrieved);
	}

	@Test
	public void testEditAfterAddingReturnsOriginallyAdded() throws EmployeeDatabaseException {
		Employee eAdded = new Employee("E1", "Martin Rist", "51 St Leonards Rd, Exeter, Devon");
		database.addEmployee(eAdded);
		eAdded.setName("David Copperfield");
		Employee eRetrieved = database.getEmployeeById("E1");
		assertNotEquals(eAdded, eRetrieved);
	}

	@Test
	public void testEditAfterRetrievalLeavesEmployeeUnchanged() throws EmployeeDatabaseException {
		Employee eAdded = new Employee("E1", "Martin Rist", "51 St Leonards Rd, Exeter, Devon");
		database.addEmployee(eAdded);
		Employee eRetrieved = database.getEmployeeById("E1");
		eRetrieved.setName("David Copperfield");
		Employee eRetrievedAgain = database.getEmployeeById("E1");
		assertEquals(eAdded, eRetrievedAgain);
	}

	@Test
	public void testRetrieveNonExistentEmployeeReturnsNull() {
		Employee eRetrieved = database.getEmployeeById("E1");
		assertNull(eRetrieved);
	}

	@Test
	public void testRetrieveEmployeeByEmptyIdReturnsNull() {
		Employee eRetrieved = database.getEmployeeById("");
		assertNull(eRetrieved);
	}

	@Test
	public void testRetrieveEmployeeByNullIdReturnsNull() {
		Employee eRetrieved = database.getEmployeeById(null);
		assertNull(eRetrieved);
	}

	@Test
	public void testRemoveEmployeeFromEmptyDatabaseReturnsFalse() {
		assertFalse(database.removeEmployee("ID"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveEmployeeWithNullIdThrowsIllegalArgumentException() {
		database.removeEmployee(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveEmployeeWithEmptyIdThrowsIllegalArgumentException() {
		database.removeEmployee("");
	}

	@Test
	public void testRemoveEmployeeNotInDatabaseReturnsFalse() throws EmployeeDatabaseException {
		Employee e = new Employee("E1", "Name", "Address");
		database.addEmployee(e);
		assertFalse(database.removeEmployee("E2"));
	}

	@Test
	public void testRemoveEmployeeInDatabaseReturnsTrue() throws EmployeeDatabaseException {
		Employee e = new Employee("E1", "name", "address");
		database.addEmployee(e);
		assertTrue(database.removeEmployee("E1"));
	}

	@Test
	public void testRemoveEmployeeInDatabaseRemovesIt() throws EmployeeDatabaseException {
		Employee e = new Employee("E1", "name", "address");
		database.addEmployee(e);
		database.removeEmployee("E1");
		assertNull(database.getEmployeeById("E1"));
		assertEquals(0, database.getNumberOfEmployees());
	}

	@Test
	public void testRemoveEmployeeInDatabaseReducesNumberOfEmployees() throws EmployeeDatabaseException {
		Employee e = new Employee("E1", "name", "address");
		database.addEmployee(e);
		database.removeEmployee("E1");
		assertEquals(0, database.getNumberOfEmployees());
	}

}
