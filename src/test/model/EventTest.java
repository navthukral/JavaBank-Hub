package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    private Event e1;
    private Event e2;

    @BeforeEach
    public void setUp() {
        this.e1 = new Event("Account added");
        this.e2 = new Event("Deposit Successful");
    }

    @Test
    public void TestConstructor() {
        assertEquals("Account added", e1.getDescription());
        assertEquals("Deposit Successful", e2.getDescription());
    }

    @Test
    public void TestGetDate() {
        assertEquals(Calendar.getInstance().getTime().toString(), e1.getDate().toString());
        assertEquals(Calendar.getInstance().getTime().toString(), e2.getDate().toString());
    }

    @Test
    public void TestEqualsNullObject() {
        assertFalse(e1.equals(null));
    }

    @Test
    public void TestEqualsDifferentData() {
        assertFalse(e1.equals("Withdraw Successful"));
    }

    @Test
    public void TestEqualsFalseDifferentDescriptionSameTime() {
        assertFalse(e1.equals(e2));
    }

    @Test
    public void TestEqualsSameDescriptionDifferentTime() throws InterruptedException {
        Thread.sleep(1000);
        Event e3 = new Event(e1.getDescription());
        assertFalse(e1.equals(e3));
    }

    @Test
    public void TestDifferentDescriptionDifferentTime() throws InterruptedException {
        Thread.sleep(1000);
        Event e3 = new Event(e2.getDescription());
        assertFalse(e1.equals(e3));
    }

    @Test
    public void TestHashCode() {
        assertEquals(-1052445337, e1.hashCode());
    }

    @Test
    public void TestToString() {
        assertEquals(e1.getDate()+ "\n" +
                "Account added", e1.toString());
    }

    @Test
    public void TestBothDescriptionAndDateEqual() {
        Event e3 = new Event("WOW");
        Event e4 = new Event("WOW");
        assertTrue(e3.equals(e4));
        assertTrue(e4.equals(e3));
    }
}
