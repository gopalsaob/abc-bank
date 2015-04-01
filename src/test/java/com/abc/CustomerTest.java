package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testCustomerStatementForMultiAccounts(){
        Customer henry = new Customer("Henry");
        Account checkingAccount = openAccount(henry, AccountType.CHECKING);
        Account savingsAccount = openAccount(henry, AccountType.SAVINGS);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testCustomerStatementForCustomerHavingMaxSavingsAccount() {
        verifyCustomerStatement(AccountType.MAXI_SAVINGS, "Maxi Savings Account");
    }

    @Test
    public void testCustomerStatementForCustomerHavingCheckingAccount() {
        verifyCustomerStatement(AccountType.CHECKING, "Checking Account");
    }

    @Test
    public void testCustomerStatementForCustomerHavingSavingsAccount() {
        verifyCustomerStatement(AccountType.SAVINGS, "Savings Account");
    }

    private void verifyCustomerStatement(AccountType accountType, String accountNameInStatement) {
        Customer henry = new Customer("Henry");
        Account account = openAccount(henry, accountType);
        account.deposit(100.0);
        assertEquals("Statement for Henry\n" +
                "\n" +
                accountNameInStatement + "\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Total In All Accounts $100.00", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar");
        openAccount(oscar, AccountType.SAVINGS);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = new Customer("Oscar");
        openAccount(oscar, AccountType.SAVINGS);
        openAccount(oscar, AccountType.CHECKING);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    private Account openAccount(Customer customer, AccountType accountType) {
        return customer.openAccount(accountType);
    }
}
