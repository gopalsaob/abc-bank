package com.abc;

import com.abc.account.domain.Account;
import com.abc.account.interest.InterestAccumulator;
import com.abc.account.statement.StatementBuilder;
import com.abc.account.type.AccountType;
import com.abc.account.repository.AccountRepository;
import com.abc.account.repository.AccountRepositoryListImpl;

public class Customer {
    private String name;
    private AccountRepository accountRepository;

    public Customer(String name) {
        this.name = name;
        this.accountRepository = new AccountRepositoryListImpl();
    }

    public String getName() {
        return name;
    }

    public int openAccount(AccountType accountType) {
        Account account = Account.createAccount(accountType);
        return accountRepository.insertAccount(account);
    }

    public int openAccount(AccountType accountType, double initialDeposit) {
        int accountId = openAccount(accountType);
        depositToAccount(accountId, initialDeposit);
        return accountId;
    }

    public void depositToAccount(int accountId, double amount) {
        Account account = accountRepository.getAccount(accountId);
        account.deposit(amount);
    }

    public void withDrawFromAccount(int accountId, double amount) {
        Account account = accountRepository.getAccount(accountId);
        account.withdraw(amount);
    }

    public void transfer(int fromAccountId, int toAccountId, double transferAmount) {
        validateDifferentAccountTransfer(fromAccountId, toAccountId);
        Account fromAccount = accountRepository.getAccount(fromAccountId);
        Account toAccount = accountRepository.getAccount(toAccountId);
        fromAccount.withdraw(transferAmount);
        toAccount.deposit(transferAmount);
    }

    public int getNumberOfAccounts() {
        return accountRepository.getSize();
    }

    public double totalInterestEarned() {
        InterestAccumulator interestAccumulator = new InterestAccumulator();
        accountRepository.accept(interestAccumulator);
        return interestAccumulator.getTotal();
    }

    public String getStatement() {
        StatementBuilder statementBuilder = new StatementBuilder(name);
        accountRepository.accept(statementBuilder);
        return statementBuilder.getFinalStatement();
    }

    private void validateDifferentAccountTransfer(int fromAccountId, int toAccountId) {
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Cannot perform transfer between same account");
        }
    }
}
