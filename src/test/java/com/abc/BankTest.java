package com.abc;

import com.abc.account.type.AccountType;
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
        john.openAccount(AccountType.SAVINGS);
        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    private Customer openCheckingAccountForCustomerHavingName(String customerName) {
        return openAccount(AccountType.CHECKING, customerName);
    }

    private Customer openAccount(AccountType accountType, String customerName) {
        Customer customer = new Customer(customerName);
        customer.openAccount(accountType);
        bank.addCustomer(customer);
        return customer;
    }
}
