package ui;


import model.AccountList;
import model.Account;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

//import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;

public class BankingAppGUI extends JFrame implements ActionListener {
    private static final int WIDTH = 402;
    private static final int HEIGHT = 604;

    private JButton addAccountButton;
    private JButton loginAccountButton;
    private JButton displayButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton balanceButton;
    private JButton logoutAccountButton;
    private AccountList accountList = new AccountList();
    private Account currentAccount;
    private boolean isLoggedIn;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/accountList.json";
    private JTextField username;
    private JTextField password;
    private JTextField amount;
    private JFrame firstPage;
    private final JPanel panelBeforeLogin = new JPanel();
    private final JPanel panelAfterLogin = new JPanel();
    private final ImageIcon dollarSign = new ImageIcon("./data/dollar.jpg");
    private final JLabel picture = new JLabel(dollarSign);




    public BankingAppGUI() throws FileNotFoundException {
        super("Banking App");
        initComponents();
        initializeGraphics();
        addListeners();

    }

    // MODIFIES: this
    // EFFECTS: Initializes the fields
    private void initComponents() {
        firstPage = new JFrame("Banking App Menu");

        addAccountButton = new JButton("Add Account");
        loginAccountButton = new JButton("Login Account");
        displayButton = new JButton("Display Accounts");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        quitButton = new JButton("Quit");
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        balanceButton = new JButton("Balance");
        logoutAccountButton = new JButton("Logout");


        panelBeforeLogin.setLayout(new BoxLayout(panelBeforeLogin, BoxLayout.Y_AXIS));
        panelAfterLogin.setLayout(new BoxLayout(panelAfterLogin, BoxLayout.Y_AXIS));


        addButtonsAfterLogin(panelAfterLogin);
        addButtonsBeforeLogin(panelBeforeLogin);


        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        username = new JTextField(30);
        password = new JTextField(30);
        amount = new JTextField(10);
    }


    // MODIFIES: this
    // EFFECTS:  initialize the GUI of the application
    private void initializeGraphics() {
        alignments();

        firstPage.add(panelBeforeLogin,0);

        firstPage.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        firstPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        firstPage.setLocationRelativeTo(null);
        firstPage.setVisible(true);
        firstPage.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: sets the state of the application depending on if an account is logged in
    private void setState() {
        if (isLoggedIn) {
            firstPage.remove(panelBeforeLogin);
            firstPage.add(panelAfterLogin,0);
        } else {
            firstPage.remove(panelAfterLogin);
            firstPage.add(panelBeforeLogin, 0);
        }
        firstPage.revalidate();
        firstPage.repaint();
    }

    // MODIFIES: this
    // EFFECTS : Aligns all the elements in the panel
    private void alignments() {
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        addAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        displayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        depositButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        withdrawButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        balanceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }




    // MODIFIES: this
    // EFFECTS : adds the buttons and picture on the panel before user is logged in
    private void addButtonsBeforeLogin(JPanel panel) {
        panel.add(picture);
        panel.add(addAccountButton);
        panel.add(loginAccountButton);
        panel.add(displayButton);
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(quitButton);
    }


    // MODIFIES: this
    // EFFECTS : adds the buttons on the panel after user is logged in
    private void addButtonsAfterLogin(JPanel panel) {
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(balanceButton);
        panel.add(logoutAccountButton);
    }

    // MODIFIES: this
    // EFFECTS: Adds listeners to the buttons
    private void addListeners() {
        addAccountButton.addActionListener(this);
        loginAccountButton.addActionListener(this);
        displayButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        quitButton.addActionListener(this);
        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        balanceButton.addActionListener(this);
        logoutAccountButton.addActionListener(this);
    }




    // EFFECTS: Process action when a button is clicked on the menu
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addAccountButton) {
            addAccount();
        } else if (e.getSource() == loginAccountButton) {
            loginAccount();
        } else if (e.getSource() == displayButton) {
            displayAccount();
        } else if (e.getSource() == saveButton) {
            saveAccountList();
        } else if (e.getSource() == loadButton) {
            loadAccountList();
        }  else if (e.getSource() == quitButton) {
            quit();
        } else if (e.getSource() == depositButton) {
            deposit();
        } else if (e.getSource() == withdrawButton) {
            withdraw();
        }  else if (e.getSource() == balanceButton) {
            balance();
        }  else if (e.getSource() == logoutAccountButton) {
            logoutAccount();
        }  else {
            System.out.println("\tWrong Input, Please choose again");
        }
    }

    // MODIFIES: this
    // EFFECTS: resets al the fields
    private void resetFields() {
        username.setText("");
        password.setText("");
        amount.setText("");
    }


    // MODIFIES: this
    // EFFECTS: adds a new account in Account List
    private void addAccount() {
        JPanel addAccountPanel = new JPanel();

        initAddAccountPanel(addAccountPanel);

        addAccountPanel.setLayout(new BoxLayout(addAccountPanel, BoxLayout.Y_AXIS));

        int result = JOptionPane.showConfirmDialog(null, addAccountPanel,
                "Enter Account Details",JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Account account =
                    new Account(username.getText(), password.getText(), Double.parseDouble(amount.getText()));
            accountList.addAccount(account);
            JOptionPane.showMessageDialog(null,
                    "New Account created to the list Successfully!!!",
                    "Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            resetFields();
            currentAccount = account;
            isLoggedIn = true;
            setState();
        }
    }




    // MODIFIES: this
    // EFFECTS: helper in initializing the add account panel
    private void initAddAccountPanel(JPanel addPanel) {
        addPanel.add(Box.createVerticalStrut(10));
        addPanel.add(new JLabel("username"));
        addPanel.add(username);
        addPanel.add(Box.createVerticalStrut(10));
        addPanel.add(new JLabel("password"));
        addPanel.add(password);
        addPanel.add(Box.createVerticalStrut(10));
        addPanel.add(new JLabel("Initial Balance"));
        addPanel.add(amount);
    }





    // EFFECTS: login the account or else print "Account not found"
    private void loginAccount() {
        JPanel loginAccountPanel = new JPanel();

        initLoginAccountPanel(loginAccountPanel);

        loginAccountPanel.setLayout(new BoxLayout(loginAccountPanel, BoxLayout.Y_AXIS));

        int result = JOptionPane.showConfirmDialog(null, loginAccountPanel,
                "Enter Account Details",JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            for (Account acc : accountList.showAllAccount()) {
                if (acc.getUsername().equals(username.getText()) && acc.getPassword().equals(password.getText())) {
                    JOptionPane.showMessageDialog(null, "Login Successful", "Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                    resetFields();

                    currentAccount = acc;
                    isLoggedIn = true;
                    setState();
                    break;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Account username or password is incorrect, please try again",
                            "Unsuccessful", JOptionPane.INFORMATION_MESSAGE);
                    resetFields();
                }
            }
        }
    }



    // EFFECTS: logout of the account
    private void logoutAccount() {
        JOptionPane.showMessageDialog(null,
                "Logout Successful", "Successful",
                JOptionPane.INFORMATION_MESSAGE);
        isLoggedIn = false;
        currentAccount = null;
        setState();
    }

    // MODIFIES: this
    // EFFECTS: helper in initializing the login panel
    private void initLoginAccountPanel(JPanel addPanel) {
        addPanel.add(Box.createVerticalStrut(10));
        addPanel.add(new JLabel("username"));
        addPanel.add(username);
        addPanel.add(Box.createVerticalStrut(10));
        addPanel.add(new JLabel("password"));
        addPanel.add(password);
    }




    // MODIFIES: this
    // EFFECTS: saves the account list to file
    private void saveAccountList() {
        try {
            jsonWriter.open();
            jsonWriter.write(accountList);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,
                    String.format("Saved list of accounts to " + JSON_STORE), "Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    String.format("Unable to write to file: " + JSON_STORE), "Unsuccessful",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }



    // MODIFIES: this
    // EFFECTS: loads account list from file
    private void loadAccountList() {
        try {
            accountList = jsonReader.read();
            JOptionPane.showMessageDialog(null,
                    String.format("Loaded list of accounts from " + JSON_STORE), "Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    String.format("Unable to read from file: " + JSON_STORE), "Unsuccessful",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }



    // EFFECTS: displays all the current accounts in the account list with their details
    private void displayAccount() {
        List<Account> displayList = accountList.showAllAccount();
        JPanel displayAccountPanel = new JPanel();
        displayAccountPanel.setLayout(new BoxLayout(displayAccountPanel, BoxLayout.Y_AXIS));
        displayAccountPanel.add(Box.createVerticalStrut(10));
        displayAccountPanel.add(new JLabel("Current accounts are displayed below:"));
        displayAccountPanel.add(Box.createVerticalStrut(20));

        for (Account account : displayList) {
            addAccountDisplay(displayAccountPanel, account);

        }

        JOptionPane.showMessageDialog(null, displayAccountPanel,
                "Accounts",JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: helper in adding an account to the display account panel
    private void addAccountDisplay(JPanel displayAccountPanel, Account a) {
        // JButton getAnswerButton = new JButton("See Answer");
        displayAccountPanel.add(new JLabel(String.format("Username: %s", a.getUsername())));
        displayAccountPanel.add(Box.createVerticalStrut(5));
        displayAccountPanel.add(new JLabel(String.format("Password: %s", a.getPassword())));
        displayAccountPanel.add(Box.createVerticalStrut(5));
        displayAccountPanel.add(new JLabel(String.format("Balance: %s", a.getBalance())));
        displayAccountPanel.add(Box.createVerticalStrut(5));
        displayAccountPanel.add(Box.createVerticalStrut(20));
    }




    // MODIFIES: this
    // EFFECTS: ends the application
    private void quit() {
        printLog(EventLog.getInstance());
        System.exit(0);
        firstPage.dispose();
        firstPage.setVisible(false);
    }


    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void deposit() {
        JPanel depositPanel = new JPanel();

        initDepositPanel(depositPanel);

        depositPanel.setLayout(new BoxLayout(depositPanel, BoxLayout.Y_AXIS));

        int result = JOptionPane.showConfirmDialog(null, depositPanel,
                "Enter Deposit Amount",JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String status = currentAccount.checkDeposit(Double.parseDouble(amount.getText()));
            JOptionPane.showMessageDialog(null,
                    status, "Deposit Status",
                    JOptionPane.INFORMATION_MESSAGE);
            resetFields();
        }
    }


    // MODIFIES: this
    // EFFECTS: helper in initializing the deposit panel
    private void initDepositPanel(JPanel panel) {
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Deposit amount"));
        panel.add(amount);
    }


    // MODIFIES: this
    // EFFECTS: conducts a withdraw transaction
    private void withdraw() {
        JPanel withdrawPanel = new JPanel();

        initWithdrawPanel(withdrawPanel);

        withdrawPanel.setLayout(new BoxLayout(withdrawPanel, BoxLayout.Y_AXIS));

        int result = JOptionPane.showConfirmDialog(null, withdrawPanel,
                "Enter Withdraw Amount",JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String status = currentAccount.checkWithdraw(Double.parseDouble(amount.getText()));
            JOptionPane.showMessageDialog(null,
                    status, "Withdraw Status",
                    JOptionPane.INFORMATION_MESSAGE);
            resetFields();
        }
    }


    // MODIFIES: this
    // EFFECTS: helper in initializing the withdraw panel
    private void initWithdrawPanel(JPanel panel) {
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Withdraw amount"));
        panel.add(amount);
    }

    // EFFECTS: returns the account balance
    private void balance() {
        JOptionPane.showMessageDialog(null,
                String.format("Your account balance is :%s", currentAccount.getBalance()), "Balance",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void printLog(EventLog events) {
        for (Event event : events) {
            System.out.println(event.toString());
        }
    }

}

