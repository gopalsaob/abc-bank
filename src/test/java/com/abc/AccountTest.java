package com.abc;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AccountTest {

    private Account account;

    @Before
    public void setupAccount() {
        account = new Account(Account.CHECKING);
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

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawalAmountIsGreaterThanAccountBalance() {
        account.withdraw(1);
    }

}
