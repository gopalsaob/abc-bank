package com.abc.account.interest;

import com.abc.account.domain.Account;
import com.abc.account.domain.AccountVisitor;

public class InterestAccumulator implements AccountVisitor {

    private double total = 0;

    public void visit(Account account) {
        total += account.interestEarned();
    }

    public double getTotal() {
        return total;
    }
}
