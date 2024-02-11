package model;

import java.util.List;

// Represents the successful transactions with type and amount
public class Transactions {
    private String type;
    private double amount;



    // REQUIRES: amount > 0
    // EFFECT: construct a transaction in the account
    public Transactions(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    // EFFECT: returns the transaction type
    public String getTransactionType() {
        return type;
    }

    // REQUIRES: amount > 0
    // EFFECT: returns the amount for the transaction
    public double getTransactionAmount() {
        return amount;
    }









}
