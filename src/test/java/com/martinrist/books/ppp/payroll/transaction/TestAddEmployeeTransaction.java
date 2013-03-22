package com.martinrist.books.ppp.payroll.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.martinrist.books.ppp.payroll.database.EmployeeDatabase;
import com.martinrist.books.ppp.payroll.database.exception.EmployeeDatabaseException;
import com.martinrist.books.ppp.payroll.model.Employee;

public class TestAddEmployeeTransaction {

	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock
	EmployeeDatabase database;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testAddEmployeeTransaction() throws TransactionProcessingException, EmployeeDatabaseException {
		Transaction txn = new AddEmployeeTransaction(database, "E1", "Martin Rist", "51 St Leonards Rd");
		context.checking(new Expectations() {
			{
				oneOf(database).addEmployee(new Employee("E1", "Martin Rist", "51 St Leonards Rd"));
			}
		});
		txn.execute();
	}

	@Test(expected = TransactionProcessingException.class)
	public void testAddEmployeeTransactionWithNullId() throws TransactionProcessingException, EmployeeDatabaseException {
		Transaction txn = new AddEmployeeTransaction(database, null, "Martin Rist", "51 St Leonards Rd");
		context.checking(new Expectations() {
			{
				oneOf(database).addEmployee(new Employee(null, "Martin Rist", "51 St Leonards Rd"));
				will(throwException(new EmployeeDatabaseException()));
			}
		});
		txn.execute();
	}

	@Test(expected = TransactionProcessingException.class)
	public void testAddEmployeeTransactionWithBlankId() throws TransactionProcessingException,
					EmployeeDatabaseException {
		Transaction txn = new AddEmployeeTransaction(database, "", "Martin Rist", "51 St Leonards Rd");
		context.checking(new Expectations() {
			{
				oneOf(database).addEmployee(new Employee("", "Martin Rist", "51 St Leonards Rd"));
				will(throwException(new EmployeeDatabaseException()));
			}
		});
		txn.execute();
	}

	@Test(expected = TransactionProcessingException.class)
	public void testAddEmployeeTransactionsWithDuplicateId() throws TransactionProcessingException,
					EmployeeDatabaseException {
		Transaction txn1 = new AddEmployeeTransaction(database, "E1", "name1", "address1");
		Transaction txn2 = new AddEmployeeTransaction(database, "E1", "name1-changed", "address1-changed");

		context.checking(new Expectations() {
			{
				oneOf(database).addEmployee(new Employee("E1", "name1", "address1"));
				oneOf(database).addEmployee(new Employee("E1", "name1-changed", "address1-changed"));
				will(throwException(new EmployeeDatabaseException()));
			}
		});

		txn1.execute();
		txn2.execute();
	}

	@Test
	public void testAddEmployeeTransactionValidationNullDatabase() {
		Transaction txn = new AddEmployeeTransaction(null, "E1", "name", "address");
		TransactionValidationResults results = txn.validate();
		assertFalse(results.isValid());
		assertEquals(1, results.getErrors().size());
	}

	@Test
	public void testAddEmployeeTransactionValidationNullId() {
		Transaction txn = new AddEmployeeTransaction(database, null, "name", "address");
		TransactionValidationResults results = txn.validate();
		assertFalse(results.isValid());
		assertEquals(1, results.getErrors().size());
	}

	@Test
	public void testAddEmployeeTransactionValidationEmptyId() {
		Transaction txn = new AddEmployeeTransaction(database, "", "Martin", "address");
		TransactionValidationResults results = txn.validate();
		assertFalse(results.isValid());
		assertEquals(1, results.getErrors().size());
	}

	@Test
	public void testAddEmployeeTransactionValidationNullName() {
		Transaction txn = new AddEmployeeTransaction(database, "E1", null, "address");
		TransactionValidationResults results = txn.validate();
		assertFalse(results.isValid());
		assertEquals(1, results.getErrors().size());
	}

	@Test
	public void testAddEmployeeTransactionValidationEmptyName() {
		Transaction txn = new AddEmployeeTransaction(database, "E1", "", "address");
		TransactionValidationResults results = txn.validate();
		assertFalse(results.isValid());
		assertEquals(1, results.getErrors().size());
	}

	@Test
	public void testAddEmployeeTransactionValidationNoDatabaseEmptyIDAndName() {
		Transaction txn = new AddEmployeeTransaction(null, "", "", "address");
		TransactionValidationResults results = txn.validate();
		assertFalse(results.isValid());
		assertEquals(3, results.getErrors().size());
	}

	@Test
	public void testAddAndUndoEmployeeTransaction() throws EmployeeDatabaseException, TransactionProcessingException {
		ReversibleTransaction txn = new AddEmployeeTransaction(database, "E1", "Name", "Address");
		context.checking(new Expectations() {
			{
				oneOf(database).addEmployee(new Employee("E1", "Name", "Address"));
				oneOf(database).removeEmployee("E1");
			}
		});

		txn.execute();
		txn.reverse();

	}
}
