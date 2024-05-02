package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Reads data to the JSON file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads CourseHome from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CourseHome read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCourseHome(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses CourseHome from JSON object and returns it
    private CourseHome parseCourseHome(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        CourseHome ch = new CourseHome(name);
        addCourses(ch, jsonObject);
        addCourseLists(ch, jsonObject);
        return ch;
    }

    // MODIFIES: ch
    // EFFECTS: parses courses from JSON object and adds them to coursehome
    private void addCourses(CourseHome ch, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourse(ch, nextCourse);
        }
    }

    // MODIFIES: ch
    // EFFECTS: parses course from JSON object and adds it to coursehome
    private void addCourse(CourseHome ch, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        int credits = jsonObject.getInt("credits");
        JSONArray jsonArray = jsonObject.getJSONArray("days");
        Course c = new Course(name, startTime, endTime, credits);
        for (int i = 0; i < jsonArray.length(); i++) {
            c.addDay(jsonArray.getString(i));
        }
        ch.addCourse(c);
    }

    // MODIFIES: ch
    // EFFECTS: parses courselists from JSON object and adds them to coursehome
    private void addCourseLists(CourseHome ch, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courseLists");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourseList(ch, nextCourse);
        }
    }

    // MODIFIES: ch
    // EFFECTS: parses course from JSON object and adds it to coursehome
    private void addCourseList(CourseHome ch, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        boolean tf = jsonObject.getBoolean("timeAndNameConflicts");
        CourseList cl = new CourseList(name, tf);
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCoursesToCourseList(cl, nextCourse);
        }
        ch.addCourseList(cl);
    }

    // MODIFIES: cl, ch
    // EFFECTS: parses course from JSON object and adds it to current course list
    private void addCoursesToCourseList(CourseList cl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        int credits = jsonObject.getInt("credits");
        JSONArray jsonArray = jsonObject.getJSONArray("days");
        Course c = new Course(name, startTime, endTime, credits);
        for (int i = 0; i < jsonArray.length(); i++) {
            c.addDay(jsonArray.getString(i));
        }
        cl.addCourse(c);
    }
}
