package com.abc.account.type;

import com.abc.account.transaction.Transaction;

import java.util.List;

/**
 * Created by ishi on 1/4/15.
 */
public interface IAccountType {
    String getName();

    double computeInterest(List<Transaction> transactions);
}
