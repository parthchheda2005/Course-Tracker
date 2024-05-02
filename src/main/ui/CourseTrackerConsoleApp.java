package ui;

import model.Course;
import model.CourseHome;
import model.CourseList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Console based version of the app
public class CourseTrackerConsoleApp {
    private Scanner input;
    private CourseHome home;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/coursehome.json";

    // EFFECTS: runs the course tracker
    public CourseTrackerConsoleApp() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        home = new CourseHome("My Course Home");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runCourseTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runCourseTracker() {
        boolean dontQuit = true;
        String command = null;

        while (dontQuit) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                dontQuit = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("c")) {
            showCourses();
        } else if (command.equals("l")) {
            showCourseList();
        } else if (command.equals("save")) {
            saveCourseHome();
        } else if (command.equals("load")) {
            loadCourseHome();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: saves home to file
    private void saveCourseHome() {
        try {
            jsonWriter.open();
            jsonWriter.write(home);
            jsonWriter.close();
            System.out.println("Saved " + home.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadCourseHome() {
        try {
            home = jsonReader.read();
            System.out.println("Loaded " + home.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to your Course Tracker!");
        System.out.println("\tPlease press one to move forward:");
        System.out.println("\tc -> Courses");
        System.out.println("\tl -> Course Lists");
        System.out.println("\tsave -> Save data to file");
        System.out.println("\tload -> Load data from file");
        System.out.println("\tq -> Quit Application");
    }

    // EFFECTS: displays courses and displays menu of options to user
    private void showCourses() {
        System.out.println("\nHere are all the courses available:");
        for (Course c : home.getCourses()) {
            System.out.println("\t" + c.getName());
        }
        System.out.println("\nPlease select what you would like to do:");
        System.out.println("\ta -> Add New Course");
        System.out.println("\te -> Edit a Course");
        System.out.println("\tv -> View Course Details");
        System.out.println("\tany letter -> Back to Home Page");
        String command = input.next();
        if (command.equals("e")) {
            editCourse();
        } else if (command.equals("a")) {
            addCourse();
        } else if (command.equals("v")) {
            viewCourse();
        }
    }

    // EFFECTS: displays course details
    private void viewCourse() {
        System.out.println("Please type the name of the course to view: ");
        String command = input.next();
        Course c = null;
        for (Course course : home.getCourses()) {
            if (course.getName().equals(command)) {
                c = course;
            }
        }
        System.out.println("\n" + c.getName() + "'s details:");
        System.out.println("\tStart Time: " + c.getStartTime());
        System.out.println("\tEnd Time: " + c.getEndTime());
        System.out.println("\tCredits: " + c.getCredits());
        ArrayList<String> days = c.getDays();
        String allDays = "";
        for (int i = 0; i < days.size(); i++) {
            if (i == (days.size() - 1)) {
                allDays += days.get(i);
            } else {
                allDays += days.get(i) + ", ";
            }
        }
        System.out.println("\tDays: " + allDays);
        showCourses();
    }

    // MODIFIES: this
    // EFFECTS: edits a courses details
    private void editCourse() {
        System.out.println("Please type the name of the course to edit: ");
        String command = input.next();
        Course c = null;
        for (Course course : home.getCourses()) {
            if (course.getName().equals(command)) {
                c = course;
            }
        }
        editCourseHelper(c);
        showCourses();
    }

   // MODIFIES: this
    // EFFECTS: handles the process of editing.
    private void editCourseHelper(Course c) {
        System.out.println("Please type the name of course: ");
        String name = input.next();
        c.setName(name);
        System.out.println("Please type the start time of course: ");
        String startTime = input.next();
        c.setStartTime(startTime);
        System.out.println("Please type the end time of course: ");
        String endTime = input.next();
        c.setEndTime(endTime);
        System.out.println("Please type the number of credits for course: ");
        int credits = input.nextInt();
        c.setCredits(credits);
        System.out.println("Please type the days: ");
        String days = input.next();
        String[] temp = days.split(", ");
        c.getDays().clear();
        for (String s : temp) {
            c.addDay(s);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new course
    private void addCourse() {
        System.out.println("Please type the name of course: ");
        String name = input.next();
        System.out.println("Please type the start time of course: ");
        String startTime = input.next();
        System.out.println("Please type the end time of course: ");
        String endTime = input.next();
        System.out.println("Please type the number of credits for course: ");
        int credits = input.nextInt();
        Course c = new Course(name, startTime, endTime, credits);
        System.out.println("Please type the days: ");
        String days = input.next();
        String[] temp = days.split(", ");
        for (int i = 0; i < temp.length; i++) {
            c.addDay(temp[i]);
        }
        home.addCourse(c);
        showCourses();
    }

    // EFFECTS: displays all course lists
    private void showCourseList() {
        System.out.println("\nHere are all the Course Lists made:");
        for (CourseList cl : home.getCourseLists()) {
            System.out.println("\t" + cl.getName());
        }
        System.out.println("\nPlease select what you would like to do:");
        System.out.println("\tv -> View Course List");
        System.out.println("\tn -> Make new Course List");
        System.out.println("\tany letter -> Back to Home Page");
        String command = input.next();
        if (command.equals("v")) {
            viewCourseList();
        } else if (command.equals("n")) {
            makeNewCourseList();
        }
    }

    // MODIFIES: this
    // EFFECTS: makes a new course list
    private void makeNewCourseList() {
        CourseList cl;
        System.out.println("Please type the name of Course List: ");
        String name = input.next();
        System.out.println("Would you like to consider time conflicts when adding courses? (y/n)");
        String command = input.next();
        if (command.equalsIgnoreCase("y")) {
            cl = new CourseList(name, true);
        } else  {
            cl = new CourseList(name, false);
        }
        home.addCourseList(cl);
        showCourseList();
    }

    // EFFECTS: shows a Course List
    private void viewCourseList() {
        System.out.println("Please type the name of Course List you would like to view: ");
        String command = input.next();
        CourseList courseList = null;
        for (CourseList cl : home.getCourseLists()) {
            if (cl.getName().equals(command)) {
                courseList = cl;
            }
        }
        System.out.println(courseList.getName() + "'s Courses");
        for (Course c : courseList.getCourses()) {
            System.out.println("\t" + c.getName());
        }
        displayForViewCourseList(courseList);
        String command2 = input.next();
        if (command2.equals("a")) {
            addCourseToCourseList(courseList);
        } else if (command2.equals("r")) {
            removeCourseFromCourseList(courseList);
        } else {
            showCourseList();
        }
    }

    // EFFECTS: Diplay for viewing a course list
    public void displayForViewCourseList(CourseList courseList) {
        System.out.println("\tTotal Credits: " + courseList.totalCredits());
        System.out.println("\nPlease select what you would like to do:");
        System.out.println("\ta -> Add Course to the List");
        System.out.println("\tr -> Remove Course From the List");
        System.out.println("\tany letter -> Back to Course List Page");
    }

    // MODIFIES: this
    // EFFECTS: adds a course to a course list
    private void addCourseToCourseList(CourseList cl) {
        boolean dontQuit = true;
        Course course = null;
        System.out.println("Please type the name of Course List you would like to add: ");
        while (dontQuit) {
            String command = input.next();
            for (Course c : home.getCourses()) {
                if (c.getName().equals(command)) {
                    course = c;
                }
            }
            if (cl.getTimeAndNameConflicts()) {
                if (cl.courseIsNew(course) && cl.noTimeConflict(course)) {
                    cl.addCourse(course);
                    dontQuit = false;
                } else {
                    System.out.println("We cannot add the course due to a time conflict.");
                }
            } else {
                cl.addCourse(course);
                dontQuit = false;
            }
        }
        showCourseList();
    }

    // REQUIRES: Course exists in CourseList
    // MODIFIES: this
    // EFFECTS: removes a course from a course list
    private void removeCourseFromCourseList(CourseList cl) {
        Course c = null;
        System.out.println("Please type the name of Course List you would like to remove: ");
        String command = input.next();
        for (Course course : cl.getCourses()) {
            String current = course.getName();
            if (current.equals(command)) {
                c = course;
            }
        }
        cl.removeCourse(c);
        showCourseList();
    }
}
