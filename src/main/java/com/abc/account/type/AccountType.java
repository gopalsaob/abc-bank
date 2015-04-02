package com.abc.account.type;

import com.abc.account.transaction.DateProvider;
import com.abc.account.transaction.Transaction;
import com.abc.account.transaction.TransactionUtil;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Date;
import java.util.List;

public enum AccountType implements IAccountType {

    CHECKING {
        public String getName() {
            return "Checking Account";
        }

        public double computeInterest(List<Transaction> transactions) {
            double amount = calculateAmount(transactions);
            return amount * InterestRate.CHECKING;
        }
    }, SAVINGS {
        public String getName() {
            return "Savings Account";
        }

        public double computeInterest(List<Transaction> transactions) {
            double amount = calculateAmount(transactions);
            if (amount <= 1000) {
                return amount * InterestRate.SAVING_LESS_THAN_OR_EQUAL_TO_THOUSAND;
            }
            return getInterestForFirstThousand() + (amount-1000) * InterestRate.SAVING_GREATER_THAN_THOUSAND;
        }

        private int getInterestForFirstThousand() {
            return 1;
        }
    }, MAXI_SAVINGS {
        public String getName() {
            return "Maxi Savings Account";
        }

        public double computeInterest(List<Transaction> transactions) {
            double amount = calculateAmount(transactions);
            for (Transaction transaction : transactions) {
                if (isWithdrawalWithin10Days(transaction)) {
                    return amount * 0.001;
                }
            }
            return amount * 0.05;
        }

        private boolean isWithdrawalWithin10Days(Transaction transaction) {
            if (transaction.getAmount() >= 0) {
                return false;
            }
            Date currentDate = DateProvider.getInstance().now();
            Days d = Days.daysBetween(new DateTime(transaction.getTransactionDate()), new DateTime(currentDate));
            int days = d.getDays();
            if (days > 10 ) {
                return false;
            }
            return true;
        }
    };

    private static double calculateAmount(List<Transaction> transactions) {
        return new TransactionUtil().sumTransactions(transactions);
    }

    private static class InterestRate {
        public static final double CHECKING = 0.001;
        public static final double SAVING_LESS_THAN_OR_EQUAL_TO_THOUSAND = 0.001;
        public static final double SAVING_GREATER_THAN_THOUSAND = 0.002;
    }
}

