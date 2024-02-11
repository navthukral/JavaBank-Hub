package ui;

import model.Account;
import model.AccountList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class BankingApp {
    private static final String JSON_STORE = "./data/accountList.json";
    private AccountList accountList;
    private Account currentAccount;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private boolean isLoggedIn;

    // EFFECTS: constructs account list and runs the banking application
    public BankingApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        accountList = new AccountList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBankingApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runBankingApp() {
        boolean keepGoing = true;
        String command = null;

        initializesAccounts();

        while (keepGoing) {
            if (isLoggedIn) {
                loggedInMenu();
            } else {
                displayMenu();
            }
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                if (isLoggedIn) {
                    loggedInProcessCommand(command);
                } else {
                    processCommand(command);
                }
            }
        }

        System.out.println("\nGoodbye!");
    }


    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void initializesAccounts() {
        accountList = new AccountList();
        currentAccount = null;
        isLoggedIn = false;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tA -> add a new account");
        System.out.println("\tL -> Login into an existing account");
        System.out.println("\tS -> save accounts to file");
        System.out.println("\tLD -> load accounts from file");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays menu of options to loggedin user
    private void loggedInMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tD -> to deposit money into your account");
        System.out.println("\tW -> to withdraw money from your account");
        System.out.println("\tB -> to view your account balance");
        System.out.println("\tO -> to logout your account");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            createAccount();
        } else if (command.equals("l")) {
            loginAccount();
        } else if (command.equals("s")) {
            saveAccounts();
        } else if (command.equals("ld")) {
            loadAccounts();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void saveAccounts() {
        try {
            jsonWriter.open();
            jsonWriter.write(accountList);
            jsonWriter.close();
            System.out.println("Saved list of accounts to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void loadAccounts() {
        try {
            accountList = jsonReader.read();
            System.out.println("Loaded list of accounts from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void loggedInProcessCommand(String command) {
        if (command.equals("d")) {
            deposit();
        } else if (command.equals("w")) {
            withdraw();
        } else if (command.equals("b")) {
            balance();
        } else if (command.equals("o")) {
            logoutAccount();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // EFFECTS: login the account or else print "Account not found"
    private void loginAccount() {
        System.out.println("Enter your account username:");
        String accUsername = input.next();
        System.out.println("Enter your password");
        String password = input.next();
        String message = "";
        for (Account acc : accountList.showAllAccount()) {
            if (acc.getUsername().equals(accUsername) && acc.getPassword().equals(password)) {
                message = "Login Successful";
                currentAccount = acc;
                isLoggedIn = true;
                break;
            } else {
                message = "Account not found";
            }
        }
        System.out.println(message);
    }


    // EFFECTS: logout of the account
    private void logoutAccount() {
        System.out.println("You are Logged out.");
        isLoggedIn = false;
        currentAccount = null;
    }

    // MODIFIES: this
    // EFFECTS: creates a new account
    private void createAccount() {
        System.out.println("Enter your desired username:");
        String accUsername = input.next();
        System.out.println("Enter your password:");
        String password = input.next();
        System.out.println("Enter initial balance for your account:");
        int initBalance = input.nextInt();

        Account acc = new Account(accUsername,password,initBalance);

        accountList.addAccount(acc);

        System.out.println(accountList.showAllAccount());

        System.out.println("Your account has been created successfully");

        currentAccount = acc;

        isLoggedIn = true;
    }

    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void deposit() {
        System.out.println("Enter the amount you want to deposit");
        double depositAmount = input.nextDouble();
        String status = currentAccount.checkDeposit(depositAmount);
        System.out.println(status);
    }

    // MODIFIES: this
    // EFFECTS: conducts a withdraw transaction
    private void withdraw() {
        System.out.println("Enter the amount you want to withdraw");
        double withdrawAmount = input.nextDouble();
        String status = currentAccount.checkWithdraw(withdrawAmount);
        System.out.println(status);
    }

    // EFFECTS: returns the account balance
    private void balance() {
        System.out.println("Your account balance is:");
        System.out.println(currentAccount.getBalance());
    }

}
