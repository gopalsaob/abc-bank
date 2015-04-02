package com.abc;

import com.abc.account.type.AccountType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testCustomerStatementForMultiAccounts(){
        Customer henry = new Customer("Henry");
        henry.openAccount(AccountType.CHECKING, 100.0);
        int savingsAccountIndex = henry.openAccount(AccountType.SAVINGS, 4000.0);
        henry.withDrawFromAccount(savingsAccountIndex, 200.0);

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
        henry.openAccount(accountType, 100.0);
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
        oscar.openAccount(AccountType.SAVINGS);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = createTwoAccountCustomer();
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositWhenIllegalAccountId() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(AccountType.SAVINGS);
        oscar.depositToAccount(-1, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawalWhenIllegalAccountId() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(AccountType.SAVINGS, 20);
        oscar.withDrawFromAccount(-1, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferWhenFromIndexIsIllegal() {
        Customer oscar = createTwoAccountCustomer();
        oscar.transfer(-1, 1, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferWhenToIndexIsIllegal() {
        Customer oscar = createTwoAccountCustomer();
        oscar.transfer(0, 2, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferToSameAccount() {
        Customer oscar = createTwoAccountCustomer();
        oscar.transfer(0, 0, 20);
    }

    @Test
    public void testTransfer() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(AccountType.SAVINGS, 50);
        oscar.openAccount(AccountType.CHECKING);
        oscar.transfer(0, 1, 20);
        assertEquals("Statement for Oscar\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $50.00\n" +
                "  withdrawal $20.00\n" +
                "Total $30.00\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $20.00\n" +
                "Total $20.00\n" +
                "\n" +
                "Total In All Accounts $50.00", oscar.getStatement());
    }

    private Customer createTwoAccountCustomer() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(AccountType.SAVINGS);
        oscar.openAccount(AccountType.CHECKING);
        return oscar;
    }
}
