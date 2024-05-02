package persistence;

import model.Course;
import model.CourseHome;
import model.CourseList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CourseHome ch = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCourseHome() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyCourseHome.json");
        try {
            CourseHome ch = reader.read();
            assertEquals("My course home", ch.getName());
            assertEquals(0, ch.getNumCourseLists());
            assertEquals(0, ch.getNumCourses());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCourseHome() {
        JsonReader reader = new JsonReader("./data/testWriterCourseHome.json");
        try {
            Course c1 = new Course("BIOL111", "12:30", "14:00", 3);
            Course c2 = new Course("CPSC210", "10:00", "11:00", 4);
            c1.addDay("Monday");
            c1.addDay("Friday");
            c2.addDay("Monday");
            c2.addDay("Wednesday");
            c2.addDay("Friday");
            CourseList test = new CourseList("test", false);
            test.addCourse(c1);
            CourseHome ch = reader.read();
            assertEquals("My course home", ch.getName());
            List<Course> courses = ch.getCourses();
            List<CourseList> courseLists = ch.getCourseLists();
            assertEquals(2, courses.size());
            assertSameCourse(c1, courses.get(0));
            assertSameCourse(c2, courses.get(1));
            assertEquals(1, courseLists.size());
            assertSameCourseList(test, courseLists.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    private void assertSameCourse(Course c1, Course c2) {
        assertEquals(c1.getName(), c2.getName());
        assertEquals(c1.getStartTime(), c2.getStartTime());
        assertEquals(c1.getEndTime(), c2.getEndTime());
        assertEquals(c1.getCredits(), c2.getCredits());
        assertEquals(c1.getDays(), c2.getDays());
    }

    private void assertSameCourseList(CourseList cl1, CourseList cl2) {
        assertEquals(cl1.getName(), cl2.getName());
        assertEquals(cl1.getTimeAndNameConflicts(), cl2.getTimeAndNameConflicts());
        assertEquals(cl1.getCourses().size(), cl2.getCourses().size());
        for (int i = 0; i < cl1.getCourses().size(); i++) {
            assertSameCourse(cl1.getCourses().get(i), cl2.getCourses().get(i));
        }
    }
}
