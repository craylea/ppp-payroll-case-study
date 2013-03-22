package com.martinrist.books.ppp.payroll.database.exception;

public class DuplicateEmployeeException extends EmployeeDatabaseException {

	private static final long serialVersionUID = -4714218338176231618L;

	public DuplicateEmployeeException() {
		super();
	}

	public DuplicateEmployeeException(String message) {
		super(message);
	}

}
