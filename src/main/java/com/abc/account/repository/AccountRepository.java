package com.abc.account.repository;

import com.abc.account.domain.AccountVisitor;
import com.abc.account.domain.Account;

public interface AccountRepository {
    int insertAccount(Account account);

    Account getAccount(int accountId);

    int getSize();

    void accept(AccountVisitor accountVisitor);
}
