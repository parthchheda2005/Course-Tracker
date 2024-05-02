package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCourseList {
    private Course c1;
    private Course c2;
    private Course c3;
    private Course c4;
    private Course c5;
    private Course c6;
    private Course c7;
    private CourseList cl1;

    @BeforeEach
    public void setup() {
        c1 = new Course("CPSC210", "10:00", "11:00", 4);
        c2 = new Course("LING100", "09:00", "10:30", 3);
        c3 = new Course("BIOL111", "12:30", "14:00", 3);
        c4 = new Course("CHEM111", "13:00", "15:00", 4);
        c5 = new Course("BIOL111", "10:00", "11:00", 3);
        c6 = new Course("CPSC121", "10:30", "12:30", 4);
        c7 = new Course("CPSC121", "09:00", "14:00", 4);
        cl1 = new CourseList("test", true);
    }

    @Test
    public void testStartTimeToDoubleTime() {
        assertEquals(10.00, cl1.startTimeToDoubleTime(c1));
        assertEquals(9.00, cl1.startTimeToDoubleTime(c2));
        assertEquals(12.50, cl1.startTimeToDoubleTime(c3));
    }

    @Test
    public void testEndTimeToDoubleTime() {
        assertEquals(11.00, cl1.endTimeToDoubleTime(c1));
        assertEquals(10.50, cl1.endTimeToDoubleTime(c2));
        assertEquals(14.00, cl1.endTimeToDoubleTime(c3));
    }

    @Test
    public void testCourseIsNew() {
        cl1.addCourse(c3);
        assertFalse(cl1.courseIsNew(c5));
        assertTrue(cl1.courseIsNew(c1));
        assertTrue(cl1.courseIsNew(c2));
    }

    @Test
    public void testNoTimeConflictEndTimeConflictNoDayConflict() {
        cl1.addCourse(c1);
        c1.addDay("Friday");
        c2.addDay("Monday");
        assertTrue(cl1.noTimeConflict(c2));
    }
    @Test
    public void testNoTimeConflictEndTimeConflictWithDayConflict() {
        cl1.addCourse(c1);
        c1.addDay("Friday");
        c2.addDay("Friday");
        assertFalse(cl1.noTimeConflict(c2));
    }

    @Test
    public void testNoTimeConflictEndTimeConflictWithDayConflict2() {
        cl1.addCourse(c2);
        c1.addDay("Friday");
        c2.addDay("Friday");
        assertFalse(cl1.noTimeConflict(c1));
    }

    @Test
    public void testNoTimeConflictStartTimeConflictWithDayConflict() {
        cl1.addCourse(c3);
        c3.addDay("Tuesday");
        c4.addDay("Tuesday");
        assertFalse(cl1.noTimeConflict(c4));
    }

    @Test
    public void testNoTimeConflictStartTimeConflictWithDayConflict2() {
        cl1.addCourse(c4);
        c3.addDay("Tuesday");
        c4.addDay("Tuesday");
        assertFalse(cl1.noTimeConflict(c3));
    }


    @Test
    public void testNoTimeConflictStartTimeConflictWithNoDayConflict() {
        cl1.addCourse(c3);
        c3.addDay("Tuesday");
        c4.addDay("Wednesday");
        assertTrue(cl1.noTimeConflict(c4));
    }

    @Test
    public void testNoTimeConflictStartTimeConflictWithNoDayConflict2() {
        cl1.addCourse(c4);
        c3.addDay("Tuesday");
        c4.addDay("Wednesday");
        assertTrue(cl1.noTimeConflict(c3));
    }

    @Test
    public void testNoTimeConflictSameTimeWithNoDayConflict() {
        cl1.addCourse(c1);
        c1.addDay("Tuesday");
        c5.addDay("Wednesday");
        assertTrue(cl1.noTimeConflict(c5));
    }
    @Test
    public void testNoTimeConflictSameTimeWithDayConflict() {
        cl1.addCourse(c1);
        c1.addDay("Monday");
        c5.addDay("Monday");
        assertFalse(cl1.noTimeConflict(c5));
    }

    @Test
    public void testNoTimeConflictDifferentTimeWithDayConflictSameTime() {
        cl1.addCourse(c1);
        c1.addDay("Monday");
        c4.addDay("Monday");
        assertTrue(cl1.noTimeConflict(c4));
    }

    @Test
    public void testNoTimeConflictDirectlyBefore() {
        cl1.addCourse(c3);
        c3.addDay("Monday");
        c6.addDay("Monday");
        assertTrue(cl1.noTimeConflict(c6));
    }

    @Test
    public void testNoTimeConflictDirectlyAfter() {
        cl1.addCourse(c2);
        c2.addDay("Monday");
        c6.addDay("Monday");
        assertTrue(cl1.noTimeConflict(c6));
    }

    @Test
    public void testNoTimeConflictNotFirstDayInListHasConflict() {
        cl1.addCourse(c1);
        c1.addDay("Monday");
        c1.addDay("Wednesday");
        c5.addDay("Tuesday");
        c5.addDay("Wednesday");
        c5.addDay("Thursday");
        assertFalse(cl1.noTimeConflict(c5));
    }

    @Test
    public void testNoTimeConflictNotFirstDayInListHasConflict2() {
        cl1.addCourse(c5);
        c1.addDay("Monday");
        c1.addDay("Wednesday");
        c5.addDay("Tuesday");
        c5.addDay("Wednesday");
        c5.addDay("Thursday");
        assertFalse(cl1.noTimeConflict(c1));
    }

    @Test
    public void testNoTimeConflictNotFirstDayInListHasConflictStillTrue() {
        cl1.addCourse(c1);
        c1.addDay("Monday");
        c1.addDay("Wednesday");
        c3.addDay("Tuesday");
        c3.addDay("Wednesday");
        c3.addDay("Thursday");
        assertTrue(cl1.noTimeConflict(c3));
    }

    @Test
    public void testNoTimeConflictDirectlyAfterMultipleDays() {
        cl1.addCourse(c2);
        c2.addDay("Monday");
        c2.addDay("Tuesday");
        c6.addDay("Friday");
        c6.addDay("Tuesday");
        c6.addDay("Wednesday");
        c6.addDay("Friday");
        assertTrue(cl1.noTimeConflict(c6));
    }

    @Test
    public void testNoTimeConflictSameTimeWithDayConflictMultipleDays() {
        cl1.addCourse(c1);
        c1.addDay("Monday");
        c1.addDay("Tuesday");
        c1.addDay("Thursday");
        c5.addDay("Wednesday");
        c5.addDay("Thursday");
        assertFalse(cl1.noTimeConflict(c5));
    }

    @Test
    public void testNoTimeConflictSameStartTimeSameDay() {
        cl1.addCourse(c2);
        c2.addDay("Monday");
        c7.addDay("Monday");
        assertFalse(cl1.noTimeConflict(c7));
    }

    @Test
    public void testNoTimeConflictSameStartTimeSameDay2() {
        cl1.addCourse(c7);
        c2.addDay("Monday");
        c7.addDay("Monday");
        assertFalse(cl1.noTimeConflict(c2));
    }

    @Test
    public void testNoTimeConflictSameEndTimeSameDay() {
        cl1.addCourse(c3);
        c3.addDay("Monday");
        c7.addDay("Monday");
        assertFalse(cl1.noTimeConflict(c7));
    }

    @Test
    public void testNoTimeConflictSameEndTimeSameDay2() {
        cl1.addCourse(c7);
        c3.addDay("Monday");
        c7.addDay("Monday");
        assertFalse(cl1.noTimeConflict(c3));
    }

    @Test
    public void testTotalCredits() {
        assertEquals(0, cl1.totalCredits());
        cl1.addCourse(c1);
        assertEquals(4, cl1.totalCredits());
        cl1.addCourse(c2);
        assertEquals(7, cl1.totalCredits());
        cl1.addCourse(c3);
        assertEquals(10, cl1.totalCredits());
    }

    @Test
    public void testGetCourses() {
        ArrayList<Course> temp = new ArrayList<Course>();
        assertEquals(cl1.getCourses(), temp);
    }

    @Test
    public void testGetName() {
        assertEquals("test", cl1.getName());
    }

    @Test
    public void testSetName() {
        assertEquals("test", cl1.getName());
        cl1.setName("name");
        assertEquals("name", cl1.getName());
    }

    @Test
    public void testRemoveCourse() {
        cl1.addCourse(c1);
        cl1.addCourse(c2);
        cl1.addCourse(c3);
        cl1.removeCourse(c1);
        assertFalse(cl1.getCourses().contains(c1));
        assertTrue(cl1.getCourses().contains(c2));
        assertTrue(cl1.getCourses().contains(c3));
        assertEquals(2, cl1.getCourses().size());
    }

    @Test
    public void testSetTimeAndNameConflict() {
        assertTrue(cl1.getTimeAndNameConflicts());
        cl1.setTimeAndNameConflicts(false);
        assertFalse(cl1.getTimeAndNameConflicts());
    }
}

