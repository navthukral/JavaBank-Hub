package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {
    private Account testAccount;
    private Account testAccount1;
    private Account testAccount2;
    private Account testAccountCon;

    @BeforeEach
    void setUp() {
        testAccount = new Account("Customer","hello",10);
        testAccount1 = new Account("Customer1","hello1",0);
        testAccount2 = new Account("Customer2","hello2",1000);
    }

    @Test
    void testConstructor() {
        testAccountCon = new Account("Customer","hello",10);
        assertEquals("Customer", testAccountCon.getUsername());
        assertEquals(34,testAccountCon.getAccountNumber());
        assertEquals("hello", testAccountCon.getPassword());
        assertEquals(10,testAccountCon.getBalance());
    }

    @Test
    void testZeroBalance() {
        assertEquals(0,testAccount1.getBalance());
    }

    @Test
    void testSingleDeposit() {
        testAccount.deposit(10);
        assertEquals(20, testAccount.getBalance());

        testAccount1.deposit(100);
        assertEquals(100,testAccount1.getBalance());
    }

    @Test
    void testMultipleDeposit() {
        testAccount.deposit(100);
        testAccount.deposit(30);
        assertEquals(140, testAccount.getBalance());

        testAccount1.deposit(10);
        testAccount1.deposit(50);
        testAccount1.deposit(900);
        assertEquals(960,testAccount1.getBalance());
    }

    @Test
    void testSingleWithdraw() {
        testAccount.withdraw(10);
        assertEquals(0, testAccount.getBalance());

        testAccount2.withdraw(100);
        assertEquals(900,testAccount2.getBalance());
    }

    @Test
    void testMultipleWithdraw() {
        testAccount.withdraw(1);
        testAccount.withdraw(3);
        assertEquals(6, testAccount.getBalance());

        testAccount2.withdraw(10);
        testAccount2.withdraw(50);
        testAccount2.withdraw(900);
        assertEquals(40,testAccount2.getBalance());
    }

    @Test
    void testOverWithdraw() {
        testAccount.withdraw(1);
        testAccount.withdraw(3);
        assertEquals(6, testAccount.getBalance());

        testAccount2.withdraw(10);
        testAccount2.withdraw(50);
        assertEquals(940,testAccount2.getBalance());
    }

    @Test
    void testCheckDeposit() {
        String output1 = testAccount.checkDeposit(1000);
        assertEquals(1010, testAccount.getBalance());
        assertEquals("Deposit Successful",output1);


        String output2 = testAccount.checkDeposit(9999);
        assertEquals(11009, testAccount.getBalance());
        assertEquals("Deposit Successful",output2);

        String output3 = testAccount.checkDeposit(10000);
        assertEquals(11009, testAccount.getBalance());
        assertEquals("This is a FRAUD TRANSACTION",output3);

        String output4 = testAccount.checkDeposit(10001);
        assertEquals(11009, testAccount.getBalance());
        assertEquals("This is a FRAUD TRANSACTION",output4);
    }

    @Test
    void testCheckWithdraw() {
        String output1 = testAccount2.checkWithdraw(10000);
        assertEquals("This is a FRAUD TRANSACTION",output1);
        assertEquals(1000, testAccount2.getBalance());

        String output2 = testAccount2.checkWithdraw(10001);
        assertEquals("This is a FRAUD TRANSACTION",output2);
        assertEquals(1000, testAccount2.getBalance());

        String output3 = testAccount2.checkWithdraw(1002);
        assertEquals("Not Sufficient Balance",output3);
        assertEquals(1000, testAccount2.getBalance());

        String output4 = testAccount2.checkWithdraw(100);
        assertEquals("Withdraw Successful",output4);
        assertEquals(900, testAccount2.getBalance());
    }

    @Test
    void testSetPassword(){
        testAccountCon = new Account("Customer","hello",10);
        testAccountCon.setPassword("HELLO@123");
        assertEquals("HELLO@123", testAccountCon.getPassword());
    }
}