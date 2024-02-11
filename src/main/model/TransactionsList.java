package model;

import java.util.ArrayList;
import java.util.List;

// Represents the list of successful transactions
public class TransactionsList {
    private final List<Transactions> listOfTransactions;


    // EFFECT: constructs a list of transactions
    public TransactionsList() {
        listOfTransactions = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECT: adds a transaction in the transaction list
    public void addTransaction(Transactions transaction) {
        listOfTransactions.add(transaction);
    }


    // EFFECT: returns the list of transactions
    public List<Transactions> showAllTransactions() {
        return listOfTransactions;
    }
}
