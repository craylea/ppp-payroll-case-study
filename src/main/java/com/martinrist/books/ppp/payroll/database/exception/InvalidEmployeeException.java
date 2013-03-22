package com.martinrist.books.ppp.payroll.database.exception;

public class InvalidEmployeeException extends EmployeeDatabaseException {

	private static final long serialVersionUID = -6431096591502987934L;

	public InvalidEmployeeException() {
		super();
	}

	public InvalidEmployeeException(String message) {
		super(message);
	}

}
