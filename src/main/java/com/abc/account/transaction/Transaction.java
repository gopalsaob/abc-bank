package com.abc.account.transaction;

import java.util.Date;

public class Transaction {
    private final double amount;

    private Date transactionDate;

    public Transaction(double amount, Date transactionDate) {
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public static Transaction createImmediateTransaction(double amount) {
        return new Transaction(amount, DateProvider.getInstance().now());
    }
}
