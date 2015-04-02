package com.abc.account.statement;

import com.abc.account.transaction.Transaction;
import com.abc.account.domain.Account;
import com.abc.account.domain.AccountVisitor;

import static java.lang.Math.abs;

public class StatementBuilder implements AccountVisitor {

    private StringBuilder statement;
    private double total = 0.0;

    public StatementBuilder(String name) {
        this.statement = new StringBuilder(String.format("Statement for %s%n", name));
    }

    public void visit(Account account) {
        statement.append(String.format("%n%s%n", statementForAccount(account)));
        total += account.sumTransactions();
    }

    private String statementForAccount(Account account) {
        StringBuilder statement = new StringBuilder(account.getAccountType().getName());
        statement.append("\n");
        double total = totalUpAllTransactions(account, statement);
        statement.append("Total ");
        statement.append(toDollars(total));
        return statement.toString();
    }

    private double totalUpAllTransactions(Account account, StringBuilder statement) {
        double total = 0.0;
        for (Transaction t : account.transactions) {
            statement.append(String.format("  %s %s%n", (t.amount < 0 ? "withdrawal" : "deposit"), toDollars(t.amount)));
            total += t.amount;
        }
        return total;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public String getFinalStatement() {
        return String.format("%s%nTotal In All Accounts %s", statement.toString(), toDollars(total));
    }
}
