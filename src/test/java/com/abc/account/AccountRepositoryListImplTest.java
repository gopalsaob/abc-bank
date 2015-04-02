package com.abc.account;

import com.abc.account.domain.Account;
import com.abc.account.type.AccountType;
import com.abc.account.repository.AccountRepositoryListImpl;
import org.junit.Before;
import org.junit.Test;

public class AccountRepositoryListImplTest {
    private AccountRepositoryListImpl repositoryList;

    @Before
    public void setupAccountRepository() {
        repositoryList = new AccountRepositoryListImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenAccountIdIsLessThanZero() {
        Account account = Account.createAccount(AccountType.CHECKING);
        repositoryList.insertAccount(account);
        repositoryList.getAccount(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhenAccountIdIsGreaterThanOrEqualToListSize() {
        Account account = Account.createAccount(AccountType.CHECKING);
        repositoryList.insertAccount(account);
        repositoryList.getAccount(1);
    }
}
