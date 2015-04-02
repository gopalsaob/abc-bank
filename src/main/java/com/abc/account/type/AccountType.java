package com.abc.account.type;

public enum AccountType {

    CHECKING {
        @Override
        public String getName() {
            return "Checking Account";
        }

        @Override
        public double computeInterest(double amount) {
            return amount * InterestRate.CHECKING;
        }
    }, SAVINGS {
        @Override
        public String getName() {
            return "Savings Account";
        }

        @Override
        public double computeInterest(double amount) {
            if (amount <= 1000) {
                return amount * InterestRate.SAVING_LESS_THAN_OR_EQUAL_TO_THOUSAND;
            }
            return getInterestForFirstThousand() + (amount-1000) * InterestRate.SAVING_GREATER_THAN_THOUSAND;
        }

        private int getInterestForFirstThousand() {
            return 1;
        }
    }, MAXI_SAVINGS {
        @Override
        public String getName() {
            return "Maxi Savings Account";
        }

        @Override
        public double computeInterest(double amount) {
            if (amount <= 1000)
                return amount * InterestRate.MAXI_SAVING_LESS_THAN_THOUSAND;
            if (amount <= 2000)
                return getInterestForFirstThousand() + (amount-1000) * InterestRate.MAXI_SAVING_LESS_THAN_TWO_THOUSAND;
            return getInterestForFirstTwoThousand() + (amount-2000) * InterestRate.MAXI_SAVING_GREATER_THAN_TWO_THOUSAND;
        }

        private int getInterestForFirstThousand() {
            return 20;
        }

        private int getInterestForFirstTwoThousand() {
            return 70;
        }
    };

    public abstract String getName();

    public abstract double computeInterest(double amount);

    private static class InterestRate {
        public static final double CHECKING = 0.001;
        public static final double SAVING_LESS_THAN_OR_EQUAL_TO_THOUSAND = 0.001;
        public static final double SAVING_GREATER_THAN_THOUSAND = 0.002;
        public static final double MAXI_SAVING_LESS_THAN_THOUSAND = 0.02;
        public static final double MAXI_SAVING_LESS_THAN_TWO_THOUSAND = 0.05;
        public static final double MAXI_SAVING_GREATER_THAN_TWO_THOUSAND = 0.1;
    }
}

