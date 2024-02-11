package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an account with username, account number, password, balance
public class Account implements Writable {
    private final String username;
    private String password;
    private double balance;
    private static int nextAccountId = 1;
    private final int accountNumber;
    private final TransactionsList transactions;



    // REQUIRES: initialBalance >= 0
    // EFFECT: Constructs a new account with username, account number, password, balance and a list of transactions.
    public Account(String username, String password, double initialBalance) {
        this.username = username;
        this.password = password;
        this.accountNumber = nextAccountId++; //CHECK THIS
        this.balance = initialBalance;
        this.transactions = new TransactionsList();
    }

    // REQUIRES: amount > 0
    // EFFECT: checks if the deposit is valid or not.
    public String checkDeposit(double amount) {
        if (fraudDeposit(amount)) {
            return fraud();
        } else {
            deposit(amount);
            return "Deposit Successful";

        }
    }


    // REQUIRES: amount > 0
    // EFFECT: checks if the withdraw is valid or not.
    public String checkWithdraw(double amount) {
        if  (fraudWithdraw(amount)) {
            return fraud();

        } else if (!checkSufficientBalance(amount)) {
            return "Not Sufficient Balance";

        } else {
            withdraw(amount);
            return "Withdraw Successful";

        }
    }


    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECT: deposits (adds) the amount in the account balance.
    public void deposit(double amount) {
        Transactions transaction = new Transactions("deposit", amount);
        transactions.addTransaction(transaction);
        balance += amount;
        EventLog.getInstance().logEvent(new Event(Double.toString(amount) + " is added to account."));
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECT: withdraw (subtracts) the amount from the account balance
    public void withdraw(double amount) {
        Transactions transaction = new Transactions("withdraw", amount);
        transactions.addTransaction(transaction);
        balance -= amount;
        EventLog.getInstance().logEvent(new Event(Double.toString(amount) + " is withdrawn from account."));
    }


    // EFFECT: returns true if the withdrawal is above 10000 dollars
    public Boolean fraudWithdraw(double  amount) {
        return (amount >= 10000);
    }

    // EFFECT: returns true the account have Sufficient Balance
    public Boolean checkSufficientBalance(double amount) {
        return (balance - amount >= 0);
    }


    // EFFECT: returns true if the deposit amount is above 10000 dollars
    public Boolean fraudDeposit(double  amount) {
        return amount >= 10000;
    }

    // EFFECT: returns string saying it's a fraud
    public String fraud() {
        return ("This is a FRAUD TRANSACTION");
    }



    // EFFECT: returns the account username.
    public String getUsername() {
        return username;
    }

    // EFFECT: returns the account number.
    public int getAccountNumber() {
        return accountNumber;
    }

    // EFFECT: returns the balance in the account.
    public double  getBalance() {
        EventLog.getInstance().logEvent(new Event(Double.toString(balance) + " is the total balance in the account."));
        return balance;
    }

    // MODIFIES: this
    // EFFECT: changes the account password.
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }


    // EFFECT: returns the account password.
    public String getPassword() {
        return password;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        json.put("balance", balance);
        json.put("accountNumber", accountNumber);
        json.put("transactions", transactions);
        return json;
    }
}
