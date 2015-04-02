package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers)
            summary.append(String.format("%n - %s (%s)", c.getName(), format(c.getNumberOfAccounts(), "account")));
        return summary.toString();
    }

    private String format(int number, String word) {
        return String.format("%d %s", number, addPluralToWordBasedOnNumber(number, word));
    }

    private String addPluralToWordBasedOnNumber(int number, String word) {
        if (number == 1) {
            return word;
        }
        return word + "s";
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }
}
