package com.abc.account.transaction;

import java.util.List;

public class TransactionUtil {

    public double sumTransactions(List<Transaction> transactions) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

}
