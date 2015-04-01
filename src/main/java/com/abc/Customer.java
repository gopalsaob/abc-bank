package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Account openAccount(AccountType accountType) {
        Account account = Account.createAccount(accountType);
        accounts.add(account);
        return account;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder(String.format("Statement for %s\n", name));
        double total = 0.0;
        for (Account a : accounts) {
            statement.append(String.format("\n%s\n", statementForAccount(a)));
            total += a.sumTransactions();
        }
        statement.append(String.format("\nTotal In All Accounts %s", toDollars(total)));
        return statement.toString();
    }

    private String statementForAccount(Account account) {
        String s = account.getAccountType().getName() + "\n";
        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : account.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
