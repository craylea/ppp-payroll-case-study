package com.martinrist.books.ppp.payroll.transaction;

public interface ReversibleTransaction extends Transaction {

	public abstract void reverse();

}
