package model;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {

    @Test
    public void TestEventLogGetInstance() {
        assertNotNull(EventLog.getInstance());
    }


    @Test
    public void TestClearLog() {
        EventLog.getInstance().clear();
        assertEquals("", EventLog.getInstance().toString());
    }

    @Test
    public void TestClearLogWithEvent() {
        Event SampleEvent = new Event("gjgjg khjhbhb");
        String result = "EventLog{events=[" +  SampleEvent.getDate() + "\n" +
                "Event log cleared.]}";
        assertNotEquals(result, EventLog.getInstance().toString());
    }

    @Test
    public void TestGetIterator() {
        assertNotNull(EventLog.getInstance().iterator());
    }
}
