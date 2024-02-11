package persistence;

import model.Account;
import model.AccountList;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads list of accounts from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AccountList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccountList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses AccountList from JSON object and returns it
    private AccountList parseAccountList(JSONObject jsonObject) {
        AccountList al = new AccountList();
        addAccounts(al, jsonObject);
        return al;
    }

    // MODIFIES: this
    // EFFECTS: parses accounts from JSON object and adds them to list of accounts
    private void addAccounts(AccountList al, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("List Of Account");
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addAccount(al, nextAccount);
        }
    }

    // MODIFIES: this
    // EFFECTS: parses account from JSON object and adds it to list of accounts
    private void addAccount(AccountList al, JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        double initialBalance = jsonObject.getDouble("balance");
        Account account = new Account(username, password, initialBalance);
        al.addAccount(account);
    }



}
