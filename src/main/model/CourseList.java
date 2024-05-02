package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// CourseList captures a list of courses
public class CourseList implements Writable {
    private ArrayList<Course> courses;
    private String name;
    private boolean timeAndNameConflicts;

    // EFFECTS: Constructs a CourseList
    public CourseList(String name, boolean timeAndNameConflicts) {
        courses = new ArrayList<Course>();
        this.timeAndNameConflicts = timeAndNameConflicts;
        this.name = name;
        EventLog.getInstance().logEvent(new Event("New CourseList " + name
                + " was created, checks for time conflicts is " + timeAndNameConflicts + "."));
    }

    // EFFECTS: Calculates total credits in courses
    public int totalCredits() {
        int output = 0;
        for (Course c : courses) {
            output = output + c.getCredits();
        }
        return output;
    }

    // REQUIRES: courses has to have a start time and end time in the format "--:--"
    // EFFECTS: checks if course c has any time conflicts with the other courses in courses
    public boolean noTimeConflict(Course c) {
        for (Course course : courses) {
            for (String day : c.getDays()) {
                if (course.getDays().contains(day)) {
                    double start = startTimeToDoubleTime(c);
                    double end = endTimeToDoubleTime(c);
                    double courseStart = startTimeToDoubleTime(course);
                    double courseEnd = endTimeToDoubleTime(course);
                    if ((courseStart <= start) && (start < courseEnd)) {
                        return false;
                    }
                    if ((courseStart < end) && (end <= courseEnd)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // EFFECTS: checks if course c is a duplicate of any other courses in courses
    public boolean courseIsNew(Course c) {
        for (Course course : courses) {
            if (c.getName().equals(course.getName())) {
                return false;
            }
        }
        return true;
    }

    // REQUIRES: Course with times set up
    // EFFECTS: Converts the string startTime to a double
    public double startTimeToDoubleTime(Course c) {
        String s = c.getStartTime();
        double hour = Integer.parseInt(s.substring(0, 2));
        double min = Integer.parseInt(s.substring(3,5));
        double startTime = hour + (min / 60);
        return startTime;
    }

    // REQUIRES: Course with times set up
    // EFFECTS: Converts the string endTime to a double
    public double endTimeToDoubleTime(Course c) {
        String s = c.getEndTime();
        double hour = Integer.parseInt(s.substring(0, 2));
        double min = Integer.parseInt(s.substring(3,5));
        double endTime = hour + (min / 60);
        return endTime;
    }

    // MODIFIES: this
    // EFFECTS: adds a course to the list
    public void addCourse(Course c) {
        courses.add(c);
        EventLog.getInstance().logEvent(new Event(c.getName() + " was added to " + this.name));
    }

    // REQUIRES: c is a course in the list
    // MODIFIES: this
    // EFFECTS: removes course from list
    public void removeCourse(Course c) {
        courses.remove(c);
        EventLog.getInstance().logEvent(new Event(c.getName() + " was removed from " + this.name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public boolean getTimeAndNameConflicts() {
        return timeAndNameConflicts;
    }

    public void setTimeAndNameConflicts(boolean timeAndNameConflicts) {
        this.timeAndNameConflicts = timeAndNameConflicts;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("timeAndNameConflicts", timeAndNameConflicts);
        JSONArray jsonArray = new JSONArray();
        for (Course c : courses) {
            jsonArray.put(c.toJson());
        }
        json.put("courses", jsonArray);
        return json;
    }

}
