package com.abc.account;

import com.abc.account.transaction.Transaction;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = Transaction.createImmediateTransaction(5);
        assertTrue(t instanceof Transaction);
    }
}
