package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionsTest {
    private Transactions transaction1;
    private Transactions transaction2;

    @BeforeEach
    void setUp() {
        transaction1 = new Transactions("deposit", 10);
        transaction2 = new Transactions("withdraw", 50);

    }

    @Test
    void testConstructor(){
        assertEquals("deposit", transaction1.getTransactionType());
        assertEquals(10, transaction1.getTransactionAmount());

        assertEquals("withdraw", transaction2.getTransactionType());
        assertEquals(50, transaction2.getTransactionAmount());
    }









}
