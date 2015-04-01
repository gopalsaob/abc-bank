package com.abc;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;

public class BankTest {
    private Bank bank;

    @Before
    public void setupBank() {
        bank = new Bank();
    }

    @Test
    public void customerSummary() {
        openCheckingAccountForCustomerHavingName("John");
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void customerSummaryForTwoCustomers() {
        openCheckingAccountForCustomerHavingName("John");
        openCheckingAccountForCustomerHavingName("Wayne");
        assertEquals("Customer Summary\n - John (1 account)\n" +
                " - Wayne (1 account)", bank.customerSummary());
    }

    @Test
    public void customerSummaryForCustomerWithTwoAccounts() {
        Customer john = openCheckingAccountForCustomerHavingName("John");
        openAccount(john, Account.SAVINGS);
        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void checkingAccountInterestEarned() {
        int amount = 100;
        Account account = openAccountOfType(Account.CHECKING);
        account.deposit(amount);
        assertThat(bank.totalInterestPaid(), is(amount * 0.001));
    }

    @Test
    public void savingsAccountInterestEarnedWhenAmountIsGreaterThanThousand() {
        int amount = 1500;
        Account account = openAccountOfType(Account.SAVINGS);
        account.deposit(amount);
        assertThat(bank.totalInterestPaid(), is(1000 * 0.001 + (amount - 1000) * 0.002));
    }

    @Test
    public void testSavingsAccountInterestEarnedWhenAmountIsLessThanThousand() {
        int amount = 500;
        Account account = openAccountOfType(Account.SAVINGS);
        account.deposit(amount);
        assertThat(bank.totalInterestPaid(), is(amount * 0.001));
    }

    @Test
    public void testMaxiSavingsAccountInterestEarnedWhenAmountIsGreaterThanTwoThousand() {
        int amount = 3000;
        Account account = openAccountOfType(Account.MAXI_SAVINGS);
        account.deposit(amount);
        assertThat(bank.totalInterestPaid(), is(1000 * 0.02 + 1000 * 0.05 + (amount - 2000) * 0.1));
    }

    @Test
    public void testMaxiSavingsAccountInterestEarnedWhenAmountIsLessThanTwoThousand() {
        int amount = 1500;
        Account account = openAccountOfType(Account.MAXI_SAVINGS);
        account.deposit(amount);
        assertThat(bank.totalInterestPaid(), is(1000 * 0.02 + (amount - 1000) * 0.05));
    }

    @Test
    public void testMaxiSavingsAccountInterestEarnedWhenAmountIsLessThanOneThousand() {
        int amount = 500;
        Account account = openAccountOfType(Account.MAXI_SAVINGS);
        account.deposit(amount);
        assertThat(bank.totalInterestPaid(), is(amount * 0.02));
    }

    private Account openAccountOfType(int accountType) {
        Customer bill = new Customer("Bill");
        Account account = openAccount(bill, accountType);
        bank.addCustomer(bill);
        return account;
    }

    private Customer openCheckingAccountForCustomerHavingName(String customerName) {
        Customer customer = new Customer(customerName);
        openAccount(customer, Account.CHECKING);
        bank.addCustomer(customer);
        return customer;
    }

    private Account openAccount(Customer customer, int accountType) {
        Account account = new Account(accountType);
        customer.openAccount(account);
        return account;
    }
}
