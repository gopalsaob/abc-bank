package com.abc.account;

import com.abc.account.domain.Account;
import com.abc.account.transaction.Transaction;
import com.abc.account.type.AccountType;
import com.abc.account.type.IAccountType;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void testTransactionsVulnerabilityWhenComputingInterest() {
        IAccountType badAccountType = new IAccountType() {
            public String getName() {
                return null;
            }

            public double computeInterest(List<Transaction> transactions) {
                transactions.clear();
                return 0;
            }
        };
        account = Account.createAccount(badAccountType);
        account.deposit(100);
        account.interestEarned();
        assertThat(account.getTransactions().size(), is(1));
    }

}
