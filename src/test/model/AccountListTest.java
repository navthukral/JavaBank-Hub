package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountListTest {
    private AccountList myList;


    @BeforeEach
    void setUp() {
        myList = new AccountList();
    }
    @Test
    void testAddAccount() {
        Account account1 = new Account("username1", "hello", 10);
        myList.addAccount(account1);
        assertEquals(account1, myList.showAllAccount().get(0));

        Account account2 = new Account("username2", "bye",100 );
        myList.addAccount(account2);
        assertEquals(account2, myList.showAllAccount().get(1));

    }
}
