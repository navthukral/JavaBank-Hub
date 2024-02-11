package persistence;

import model.AccountList;
import model.Account;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    protected void checkAccount(String username, String password, double balance, Account account) {
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
        assertEquals(balance, account.getBalance());
    }
}
