package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionListTest {
    private TransactionsList myList;


    @BeforeEach
    void setUp() {
        myList = new TransactionsList();
    }
    @Test
    void testAddTransaction() {
        Transactions transaction1 = new Transactions("deposit", 10);
        myList.addTransaction(transaction1);
        assertEquals(transaction1, myList.showAllTransactions().get(0));

        Transactions transaction2 = new Transactions("withdraw", 50);
        myList.addTransaction(transaction2);
        assertEquals(transaction2, myList.showAllTransactions().get(1));

    }
}
