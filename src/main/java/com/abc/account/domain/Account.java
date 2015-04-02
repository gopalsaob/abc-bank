package com.abc.account.domain;

import com.abc.account.type.AccountType;
import com.abc.account.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private final AccountType accountType;
    public List<Transaction> transactions;

    private Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        validateAmountIsGreaterThanZero(amount);
        transactions.add(new Transaction(amount));
    }

    public void withdraw(double amount) {
        validateAmountIsGreaterThanZero(amount);
        validateAmountIsLessThanOrEqualToBalance(amount);
        transactions.add(new Transaction(-amount));
    }

    public double interestEarned() {
        double amount = sumTransactions();
        return accountType.computeInterest(amount);
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public static Account createAccount(AccountType accountType) {
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

}

