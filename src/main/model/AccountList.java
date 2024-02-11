package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;


// Represents the list of accounts
public class AccountList implements Writable {
    private final List<Account> listOfAccount;

    // EFFECT: constructs a list of account
    public AccountList() {
        listOfAccount = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECT: adds an account in the account list
    public void addAccount(Account account) {
        listOfAccount.add(account);
        EventLog.getInstance().logEvent(new Event("Account added to Account List"));
    }

    // EFFECT: returns the list of accounts
    public List<Account> showAllAccount() {
        return listOfAccount;
    }

    public int numAccounts() {
        return listOfAccount.size();
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("List Of Account", listOfAccountToJson());
        return json;
    }

    private JSONArray listOfAccountToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Account ac : listOfAccount) {
            jsonArray.put(ac.toJson());
        }

        return jsonArray;
    }


}
