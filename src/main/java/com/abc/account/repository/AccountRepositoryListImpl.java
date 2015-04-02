package com.abc.account.repository;

import com.abc.account.domain.AccountVisitor;
import com.abc.account.domain.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository represented as a list. When an account is inserted, then accountId is the index of the account
 */
public class AccountRepositoryListImpl implements AccountRepository {

    private List<Account> accounts;

    public AccountRepositoryListImpl() {
        this.accounts = new ArrayList<Account>();
    }

    public int insertAccount(Account account) {
        accounts.add(account);
        return accounts.size() - 1;
    }

    public Account getAccount(int accountId) {
        validate(accountId);
        return accounts.get(accountId);
    }

    public int getSize() {
        return accounts.size();
    }

    public void accept(AccountVisitor accountVisitor) {
        for (Account account : accounts) {
            accountVisitor.visit(account);
        }
    }

    public void validate(int index) {
        if (isInValidIndexRange(index)) {
            throw new IllegalArgumentException("Account does not exist for " + index);
        }
    }

    private boolean isInValidIndexRange(int index) {
        return index < 0 || index >= accounts.size();
    }
}
