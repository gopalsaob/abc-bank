package com.abc.account.domain;

import com.abc.account.transaction.Transaction;
import com.abc.account.transaction.TransactionUtil;
import com.abc.account.type.IAccountType;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private final IAccountType accountType;
    private final List<Transaction> transactions;

    private Account(IAccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        validateAmountIsGreaterThanZero(amount);
        transactions.add(Transaction.createImmediateTransaction(amount));
    }

    public void withdraw(double amount) {
        validateAmountIsGreaterThanZero(amount);
        validateAmountIsLessThanOrEqualToBalance(amount);
        transactions.add(Transaction.createImmediateTransaction(-amount));
    }

    public double interestEarned() {
        return accountType.computeInterest(new ArrayList<Transaction>(getTransactions()));
    }

    public double sumTransactions() {
        return new TransactionUtil().sumTransactions(transactions);
    }

    public IAccountType getAccountType() {
        return accountType;
    }

    public static Account createAccount(IAccountType accountType) {
        return new Account(accountType);
    }

    private void validateAmountIsGreaterThanZero(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }

    private void validateAmountIsLessThanOrEqualToBalance(double amount) {
        if (amount > sumTransactions()) {
            throw new IllegalArgumentException("insufficient balance");
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}

