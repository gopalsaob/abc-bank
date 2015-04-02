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
        StringBuilder accountStatement = new StringBuilder(account.getAccountType().getName());
        accountStatement.append("\n");
        double accountTotal = totalUpAllTransactions(account, accountStatement);
        accountStatement.append("Total ");
        accountStatement.append(toDollars(accountTotal));
        return accountStatement.toString();
    }

    private double totalUpAllTransactions(Account account, StringBuilder statement) {
        double accountTotal = 0.0;
        for (Transaction t : account.getTransactions()) {
            statement.append(String.format("  %s %s%n", t.getAmount() < 0 ? "withdrawal" : "deposit", toDollars(t.getAmount())));
            accountTotal += t.getAmount();
        }
        return accountTotal;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public String getFinalStatement() {
        return String.format("%s%nTotal In All Accounts %s", statement.toString(), toDollars(total));
    }
}
