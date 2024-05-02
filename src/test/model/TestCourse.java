package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestCourse {
    Course c;

    @BeforeEach
    public void setup() {
        c = new Course("", "", "", 1);
    }

    @Test
    public void testAddDay() {
        assertEquals(0, c.getDays().size());
        c.addDay("Monday");
        assertEquals(1, c.getDays().size());
        assertEquals("Monday", c.getDays().get(0));
    }

    @Test
    public void testAddMultipleDay() {
        assertEquals(0, c.getDays().size());
        c.addDay("Monday");
        c.addDay("Tuesday");
        assertEquals(2, c.getDays().size());
        assertEquals("Monday", c.getDays().get(0));
        assertEquals("Tuesday", c.getDays().get(1));
    }

    @Test
    public void testRemoveDay() {
        c.addDay("Monday");
        assertEquals(1, c.getDays().size());
        c.removeDay("Monday");
        assertEquals(0, c.getDays().size());
    }

    @Test
    public void testSetName() {
        c.setName("CPSC210");
        assertEquals("CPSC210", c.getName());
    }

    @Test
    public void testSetCredits() {
        c.setCredits(21);
        assertEquals(21, c.getCredits());
    }

    @Test
    public void testSetStartTime() {
        c.setStartTime("12:00");
        assertEquals("12:00", c.getStartTime());
    }

    @Test
    public void testSetEndTime() {
        c.setEndTime("12:00");
        assertEquals("12:00", c.getEndTime());
    }

    @Test
    public void testSetDays() {
        ArrayList<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Thursday");
        ArrayList<String> days2 = new ArrayList<>();
        days2.add("Monday");
        days2.add("Tuesday");
        c.setDays(days);
        assertEquals(days, c.getDays());
        assertNotEquals(days2, c.getDays());
        c.setDays(days2);
        assertEquals(days2, c.getDays());
        assertNotEquals(days, c.getDays());
    }
}
