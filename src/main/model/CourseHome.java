package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// CourseHome encapsulates all the data in a session
public class CourseHome implements Writable {
    private List<Course> courses;
    private List<CourseList> courseLists;
    private String name;

    // EFFECTS: constructs workroom with a name and empty list of thingies
    public CourseHome(String name) {
        this.name = name;
        courses = new ArrayList<>();
        courseLists = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    public List<CourseList> getCourseLists() {
        return courseLists;
    }

    public void addCourseList(CourseList cl) {
        courseLists.add(cl);
    }

    public int getNumCourseLists() {
        return courseLists.size();
    }

    public int getNumCourses() {
        return courses.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        JSONArray coursesJson = new JSONArray();
        for (Course c : courses) {
            coursesJson.put(c.toJson());
        }
        JSONArray courseListsJson = new JSONArray();
        for (CourseList cl : courseLists) {
            courseListsJson.put(cl.toJson());
        }
        json.put("courses", coursesJson);
        json.put("courseLists", courseListsJson);
        return json;
    }

}
