package com.abc.account;

import com.abc.account.domain.Account;
import com.abc.account.type.AccountType;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {

    private Account account;

    @Before
    public void setupAccount() {
        account = Account.createAccount(AccountType.CHECKING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositAmountIsZero() {
        account.deposit(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositAmountIsLessThanZero() {
        account.deposit(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawalAmountIsZero() {
        account.withdraw(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawalAmountIsLessThanZero() {
        account.withdraw(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawalAmountIsGreaterThanAccountBalance() {
        account.withdraw(1);
    }

}
