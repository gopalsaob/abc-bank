package com.abc.account;

import com.abc.Bank;
import com.abc.Customer;
import com.abc.account.transaction.Transaction;
import com.abc.account.type.AccountType;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountInterestTest {
    private Bank bank;

    @Before
    public void setupBank() {
        bank = new Bank();
    }

    @Test
    public void checkingAccountInterestEarned() {
        int amount = 100;
        openAccountOfType(AccountType.CHECKING, amount);
        assertThat(bank.totalInterestPaid(), is(amount * 0.001));
    }

    @Test
    public void testSavingsAccountInterestEarnedWhenAmountIsZero() {
        openAccountOfType(AccountType.SAVINGS);
        assertThat(bank.totalInterestPaid(), is(0.0));
    }

    @Test
    public void testSavingsAccountInterestEarnedWhenAmountIsLessThanThousandAndGreaterThanZero() {
        int amount = 500;
        openAccountOfType(AccountType.SAVINGS, amount);
        assertThat(bank.totalInterestPaid(), is(amount * 0.001));
    }

    @Test
    public void savingsAccountInterestEarnedWhenAmountIsGreaterThanThousand() {
        int amount = 1500;
        openAccountOfType(AccountType.SAVINGS, amount);
        assertThat(bank.totalInterestPaid(), is(1000 * 0.001 + (amount - 1000) * 0.002));
    }

    @Test
    public void testMaxiSavingsAccountInterestEarnedWhenAmountIsZero() {
        openAccountOfType(AccountType.MAXI_SAVINGS);
        assertThat(bank.totalInterestPaid(), is(0.0));
    }

    @Test
    public void testMaxiSavingsAccountInterestEarnedWhenNoWithdrawalFor10Days() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(new Transaction(500, getDate(-15)));
        transactions.add(new Transaction(-100, getDate(-11)));
        double maxiSavingsInterest = AccountType.MAXI_SAVINGS.computeInterest(transactions);
        assertThat(maxiSavingsInterest, is(400 * 0.05));
    }

    @Test
    public void testMaxiSavingsAccountInterestEarnedWhenWithdrawalWithin10Days() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(new Transaction(500, getDate(-15)));
        transactions.add(new Transaction(-100, getDate(-5)));
        double maxiSavingsInterest = AccountType.MAXI_SAVINGS.computeInterest(transactions);
        assertThat(maxiSavingsInterest, is(400 * 0.001));
    }

    private Date getDate(int numberOfDays) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, numberOfDays);
        return c.getTime();
    }

    private void openAccountOfType(AccountType accountType, double initialDeposit) {
        Customer bill = new Customer("Bill");
        bill.openAccount(accountType, initialDeposit);
        bank.addCustomer(bill);
    }

    private void openAccountOfType(AccountType accountType) {
        openAccount(accountType, "Bill");
    }

    private Customer openAccount(AccountType accountType, String customerName) {
        Customer customer = new Customer(customerName);
        customer.openAccount(accountType);
        bank.addCustomer(customer);
        return customer;
    }
}
