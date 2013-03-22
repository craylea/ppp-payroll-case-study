package com.martinrist.books.ppp.payroll.transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionValidationResults {

	private final List<String> errors = new ArrayList<>();
	private final List<String> warnings = new ArrayList<>();

	public void addError(String message) {
		if (message == null) {
			throw new IllegalArgumentException("Cannot add empty error message");
		}
		errors.add(message);
	}

	public void addWarning(String message) {
		if (message == null) {
			throw new IllegalArgumentException("Cannot add empty warning message");
		}
		warnings.add(message);
	}

	public List<String> getErrors() {
		return this.errors;
	}

	public List<String> getWarnings() {
		return this.warnings;
	}

	public boolean hasErrors() {
		return (this.errors.size() != 0);
	}

	public boolean hasWarnings() {
		return (this.warnings.size() != 0);
	}

	public boolean isValid() {
		return !hasErrors();
	}

}
