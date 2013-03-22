package com.martinrist.books.ppp.payroll.transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.martinrist.books.ppp.payroll.database.EmployeeDatabase;
import com.martinrist.books.ppp.payroll.database.exception.EmployeeDatabaseException;
import com.martinrist.books.ppp.payroll.model.Employee;

public class AddEmployeeTransaction implements ReversibleTransaction {

	private String employeeId;
	private String name;
	private String address;
	protected final EmployeeDatabase database;

	private static final Log LOG = LogFactory.getLog(AddEmployeeTransaction.class);

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public AddEmployeeTransaction(EmployeeDatabase database, String employeeId, String name, String address) {
		// TODO: Where's the right place for the database reference to be
		// injected?
		this.database = database;
		this.employeeId = employeeId;
		this.name = name;
		this.address = address;
	}

	@Override
	public void execute() throws TransactionProcessingException {
		LOG.debug("Executing transaction: " + this);
		Employee e = new Employee(this.getEmployeeId(), this.getName(), this.getAddress());
		try {
			database.addEmployee(e);
		} catch (EmployeeDatabaseException aee) {
			LOG.error("Error adding employee to database: " + aee);
			throw new TransactionProcessingException(aee);
		}

	}

	//TODO: Work out whether the validation results should be part of the object's state
	@Override
	public TransactionValidationResults validate() {

		TransactionValidationResults results = new TransactionValidationResults();

		LOG.debug("Validating transaction: " + this);

		if (database == null) {
			LOG.debug("ERROR: database is null");
			results.addError("Transaction has not been configured with a database");
		}

		if (this.getEmployeeId() == null || this.getEmployeeId().isEmpty()) {
			LOG.debug("ERROR: employeeId is null or blank");
			results.addError("EmployeeId cannot be null or blank");
		}

		if (this.getName() == null || this.getName().isEmpty()) {
			LOG.debug("ERROR: name is null or blank");
			results.addError("Name cannot be null or blank");
		}

		return results;
	}

	@Override
	public void reverse() {
		LOG.debug("Reversing transaction: " + this);
		database.removeEmployee(employeeId);
	}

	@Override
	public String toString() {
		return "AddEmployeeTransaction [employeeId="
				+ employeeId
				+ ", name="
				+ name
				+ ", address="
				+ address
				+ ", database="
				+ database
				+ "]";
	}

}
