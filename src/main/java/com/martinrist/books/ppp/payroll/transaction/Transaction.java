package com.martinrist.books.ppp.payroll.transaction;

public interface Transaction {

	public void execute() throws TransactionProcessingException;

	public TransactionValidationResults validate();

}
