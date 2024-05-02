package ui;

import model.Course;
import model.CourseHome;
import model.CourseList;
import model.EventLog;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

// CourseTrackerGUI is the GUI for my app, uses JFrame
public class CourseTrackerGUI extends JFrame implements WindowListener {
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/coursehome.json";
    private CourseHome courseHome;
    private JButton courses;
    private JButton courseList;
    private JButton save;
    private JButton load;
    private ImageIcon refreshImage;
    private ImageIcon kidStudying;
    private boolean sortCourses = false;

    // REFRESH IMAGE LINK: https://iconduck.com/icons/257090/refresh
    // STUDY HOME PAGE IMAGE LINK:
    // https://www.cleanpng.com/png-study-skills-learning-student-test-clip-art-cartoo-3962889/

    // EFFECTS: makes home gui
    public CourseTrackerGUI() {
        imagesInit();
        init();
        setTitle("Home");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        buttonsInit();
        add(new JLabel(kidStudying));
        add(courses);
        add(courseList);
        add(save);
        add(load);
        pack();
        setVisible(true);
        addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (Iterator<Event> iterator = EventLog.getInstance().iterator(); iterator.hasNext();) {
            Event event = iterator.next();
            System.out.println(event.getDescription());
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    // EFFECTS: Initialises Images
    private void imagesInit() {
        refreshImage = new ImageIcon("data/61225.png");
        Image image = refreshImage.getImage();
        Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
        refreshImage = new ImageIcon(newimg);
        kidStudying = new ImageIcon(
                "data/kisspng-study-skills-l"
                        + "earning-student-test-clip-art-cartoon-classroom-5b2c746d2e8007.3216709215296400451905.png");
        image = kidStudying.getImage();
        newimg = image.getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH);
        kidStudying = new ImageIcon(newimg);
    }

    // EFFECTS: helper function to initialise jframe
    private void jframeInit(JFrame frame) {
        frame.setSize(500, 500);
        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: saves home to file
    private void saveCourseHome() {
        try {
            jsonWriter.open();
            jsonWriter.write(courseHome);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadCourseHome() {
        try {
            courseHome = jsonReader.read();
        } catch (IOException e) {
            // pass
        }
    }

    // EFFECTS: initialises CourseHome, jsonWriter, jsonReader
    private void init() {
        courseHome = new CourseHome("My Course Home");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: initalises buttons
    private void buttonsInit() {
        courses = new JButton("Courses");
        courseList = new JButton("Course Lists");
        save = new JButton("Save");
        load = new JButton("Load");
        saveLoadInit();
        courseCourseListInit();
    }

    // EFFECTS: provides functionality for the save and load function, allowing user to save and load into the software
    private void saveLoadInit() {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCourseHome();
            }
        });
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCourseHome();
            }
        });
    }

    // EFFECTS: provides functionality to the course and courselist buttons, so that users can toggle and edit them
    private void courseCourseListInit() {
        courses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCourses();
            }
        });
        courseList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCourseLists();
            }
        });
    }

    // EFFECTS: makes JFrame that displays courses
    private void displayCourses() {
        JFrame courseFrame = new JFrame("Courses");
        JButton viewCourse = new JButton("View Course");
        JButton newCourse = new JButton("New Course");
        JButton refresh = new JButton(refreshImage);
        JPanel panel = new JPanel();
        JSplitPane splitPane = new JSplitPane();
        JList courseDisplay = new JList();
        DefaultListModel listModel = new DefaultListModel();
        displayCoursesHelper(listModel);
        buttonsInitDisplayCourse(viewCourse, newCourse, refresh, courseDisplay, courseFrame);
        courseDisplay.setModel(listModel);
        splitPane.setLeftComponent(new JScrollPane(courseDisplay));
        splitPane.setRightComponent(panel);
        courseFrame.add(splitPane);
        courseFrame.add(viewCourse);
        courseFrame.add(newCourse);
        courseFrame.add(refresh);
        jframeInit(courseFrame);
    }

    // EFFECTS: adds courses to the DefaultListModel in displayCourses
    private void displayCoursesHelper(DefaultListModel listModel) {
        for (Course c : courseHome.getCourses()) {
            listModel.addElement(c.getName());
        }
    }

    // EFFECTS: initialises buttons for DisplayCourse
    private void buttonsInitDisplayCourse(JButton viewCourse, JButton newCourse, JButton refresh,
                                          JList courseDisplay, JFrame courseFrame) {
        viewCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCourseDetails((String) courseDisplay.getSelectedValue());
            }
        });
        newCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseFrame.dispatchEvent(new WindowEvent(courseFrame, WindowEvent.WINDOW_CLOSING));
                displayCourses();
            }
        });
    }

    // EFFECTS: Makes JFrame to create a new course
    private void addCourse() {
        JFrame addCourse = new JFrame("Add Course");
        JTextField setName = new JTextField(10);
        JTextField setStartTime = new JTextField(10);
        JTextField setEndTime = new JTextField(10);
        JTextField setCredits = new JTextField(10);
        JTextField setDays = new JTextField(10);
        JLabel name = new JLabel("Name: ");
        JLabel startTime = new JLabel("Start Time: ");
        JLabel endTime = new JLabel("End Time: ");
        JLabel credits = new JLabel("Credits: ");
        JLabel days = new JLabel("Days: ");
        JButton newCourse = new JButton("Add Course");
        newCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeNewCourse(setName, setStartTime, setEndTime, setDays, setCredits, addCourse);
            }
        });
        addingButtonsToAddCourse(name, startTime, endTime, credits, days, setName, setStartTime, setEndTime, setCredits,
                setDays, addCourse, newCourse);
        jframeInit(addCourse);
    }

    // EFFECTS: adds buttons to the JFrame in AddCourse
    private void addingButtonsToAddCourse(JLabel name, JLabel startTime, JLabel endTime, JLabel credits, JLabel days,
                                          JTextField setName, JTextField setStartTime, JTextField setEndTime,
                                          JTextField setCredits, JTextField setDays,
                                          JFrame addCourse, JButton newCourse) {
        addCourse.add(name);
        addCourse.add(setName);
        addCourse.add(startTime);
        addCourse.add(setStartTime);
        addCourse.add(endTime);
        addCourse.add(setEndTime);
        addCourse.add(credits);
        addCourse.add(setCredits);
        addCourse.add(days);
        addCourse.add(setDays);
        addCourse.add(newCourse);
    }

    // MODIFIES: this
    // EFFECTS: makes new course in CourseHome
    private void makeNewCourse(JTextField setName, JTextField setStartTime, JTextField setEndTime, JTextField setDays,
                               JTextField setCredits, JFrame addCourse) {
        Course c = new Course(setName.getText(), setStartTime.getText(), setEndTime.getText(),
                Integer.parseInt(setCredits.getText()));
        String[] split = setDays.getText().split(", ");
        ArrayList<String> newDays = new ArrayList<String>();
        newDays.addAll(Arrays.asList(split));
        c.setDays(newDays);
        courseHome.addCourse(c);
        addCourse.dispatchEvent(new WindowEvent(addCourse, WindowEvent.WINDOW_CLOSING));
    }

    // EFFECTS: Displays the courseLists
    public void displayCourseLists() {
        JFrame courseListFrame = new JFrame("CourseLists");
        JButton viewCourseList = new JButton("View Course List");
        JButton newCourseList = new JButton("New Course List");
        JButton refresh = new JButton(refreshImage);
        JPanel panel = new JPanel();
        JSplitPane splitPane = new JSplitPane();
        JList courseDisplay = new JList();
        DefaultListModel listModel = new DefaultListModel();
        addCoursesToDefaultListModelDisplayCourseLists(courseDisplay, listModel);
        displayCourseListsButtonsHelper(viewCourseList, refresh, newCourseList,
                courseListFrame, courseDisplay);
        splitPane.setLeftComponent(new JScrollPane(courseDisplay));
        splitPane.setRightComponent(panel);
        courseListFrame.add(splitPane);
        courseListFrame.add(viewCourseList);
        courseListFrame.add(newCourseList);
        courseListFrame.add(refresh);
        jframeInit(courseListFrame);
    }

    // EFFECTS: adds course lists to defaultlistmodel in displayCourseLists
    private void addCoursesToDefaultListModelDisplayCourseLists(JList courseDisplay, DefaultListModel listModel) {
        for (CourseList cl : courseHome.getCourseLists()) {
            listModel.addElement(cl.getName());
        }
        courseDisplay.setModel(listModel);
    }

    // EFFECTS: initialises buttons in displayCourseLists
    private void displayCourseListsButtonsHelper(JButton viewCourseList, JButton refresh,
                                                 JButton newCourseList, JFrame courseListFrame, JList courseDisplay) {
        viewCourseList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCourseList((String) courseDisplay.getSelectedValue());
            }
        });
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseListFrame.dispatchEvent(new WindowEvent(courseListFrame, WindowEvent.WINDOW_CLOSING));
                displayCourseLists();
            }
        });
        newCourseList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeNewCourseList();
            }
        });
    }

    // EFFECTS: makes JFrame for making a new course list
    private void makeNewCourseList() {
        JFrame frame = new JFrame("New Course List");
        JButton newCourseList = new JButton("Make new Course List!");
        JLabel name = new JLabel("Name: ");
        JTextField setName = new JTextField(10);
        JLabel timeAndNameConflicts = new JLabel("Consider time and name conflicts?: ");
        String[] comboBoxArray = {"Yes", "No"};
        JComboBox setTimeAndNameConflicts = new JComboBox(comboBoxArray);
        newCourseList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newCourseListHelper(frame, setTimeAndNameConflicts, setName);
            }
        });
        frame.add(name);
        frame.add(setName);
        frame.add(timeAndNameConflicts);
        frame.add(setTimeAndNameConflicts);
        frame.add(newCourseList);
        jframeInit(frame);
    }

    // MODIFIES: this
    // EFFECTS: makes new CourseList
    private void newCourseListHelper(JFrame frame, JComboBox setTimeAndNameConflicts, JTextField setName) {
        CourseList toAdd = null;
        if (setTimeAndNameConflicts.getSelectedItem().toString().equals("Yes")) {
            toAdd = new CourseList(setName.getText(), true);
        } else if (setTimeAndNameConflicts.getSelectedItem().toString().equals("No")) {
            toAdd = new CourseList(setName.getText(), false);
        }
        courseHome.addCourseList(toAdd);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    // EFFECTS: Displays courses in a given courseList
    public void showCourseList(String name) {
        JFrame courseListDetails = new JFrame("CourseList Information");
        JButton viewCourseDetails = new JButton("View Course Details");
        JButton addCourse = new JButton("Add Course");
        JButton removeCourse = new JButton("Remove Course");
        JButton sortCoursesByCredit = new JButton("Sort Courses by Credit (low to high)");
        JButton refresh = new JButton(refreshImage);
        JPanel panel = new JPanel();
        JSplitPane splitPane = new JSplitPane();
        JList courseDisplay = new JList();
        DefaultListModel listModel = new DefaultListModel();
        addingItemsToListModelHelper(listModel, name);
        showCoursesButtonsInit1(courseDisplay, name, courseListDetails, removeCourse, refresh, sortCoursesByCredit);
        showCoursesButtonsInit2(courseDisplay, name, viewCourseDetails,addCourse);
        courseDisplay.setModel(listModel);
        splitPane.setLeftComponent(new JScrollPane(courseDisplay));
        splitPane.setRightComponent(panel);
        courseListDetails.add(splitPane);
        courseListDetails.add(viewCourseDetails);
        courseListDetails.add(addCourse);
        courseListDetails.add(removeCourse);
        courseListDetails.add(sortCoursesByCredit);
        courseListDetails.add(refresh);
        jframeInit(courseListDetails);
    }

    // EFFECTS: add courses to listModel based on if sortCourses is true
    private void addingItemsToListModelHelper(DefaultListModel listModel, String name) {
        if (sortCourses) {
            for (Course c : sortHelper(name)) {
                listModel.addElement(c.getName());
            }
        } else {
            listModelInitShowCourseList(listModel, name);
        }
    }

    // EFFECTS: adds courses in courselist to the defaultlistmodel
    private void listModelInitShowCourseList(DefaultListModel listModel, String name) {
        CourseList cl = null;
        for (CourseList courseList : courseHome.getCourseLists()) {
            if (courseList.getName().equals(name)) {
                cl = courseList;
            }
        }
        for (Course c : cl.getCourses()) {
            listModel.addElement(c.getName());
        }
    }

    // EFFECTS: Initialises buttons in showCourseList
    private void showCoursesButtonsInit1(JList courseDisplay, String name, JFrame courseListDetails,
                                         JButton removeCourse, JButton refresh, JButton sort) {
        removeCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCourseShowCourseButtonsInit1Helper(courseDisplay, name);
            }
        });
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseListDetails.dispatchEvent(new WindowEvent(courseListDetails, WindowEvent.WINDOW_CLOSING));
                sortCourses = false;
                showCourseList(name);
            }
        });
        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseListDetails.dispatchEvent(new WindowEvent(courseListDetails, WindowEvent.WINDOW_CLOSING));
                sortCourses = true;
                showCourseList(name);
            }
        });
    }

    // EFFECTS: Makes a sorted list of courses sorted by credits
    private ArrayList<Course> sortHelper(String name) {
        CourseList cl = null;
        for (CourseList courseList : courseHome.getCourseLists()) {
            if (courseList.getName().equals(name)) {
                cl = courseList;
            }
        }
        ArrayList<Integer> differentAmountsOfCredits = new ArrayList<>();
        for (Course c : cl.getCourses()) {
            if (!differentAmountsOfCredits.contains(c.getCredits())) {
                differentAmountsOfCredits.add(c.getCredits());
            }
        }
        Collections.sort(differentAmountsOfCredits);
        ArrayList<Course> sortedByCredit = new ArrayList<>();
        for (int i : differentAmountsOfCredits) {
            for (Course c : cl.getCourses()) {
                if (i == c.getCredits()) {
                    sortedByCredit.add(c);
                }
            }
        }
        return sortedByCredit;
    }

    // EFFECTS: Helper for remove course in showCourseList
    private void removeCourseShowCourseButtonsInit1Helper(JList courseDisplay, String name) {
        String courseName = (String) courseDisplay.getSelectedValue();
        Course toRemove = null;
        CourseList cl = null;
        for (CourseList courseList : courseHome.getCourseLists()) {
            if (courseList.getName().equals(name)) {
                cl = courseList;
            }
        }
        for (Course c : cl.getCourses()) {
            if (c.getName().equals(courseName)) {
                toRemove = c;
            }
        }
        cl.removeCourse(toRemove);
    }

    // EFFECTS: Initialises buttons in showCourseList
    private void showCoursesButtonsInit2(JList courseDisplay, String name,
                                         JButton viewCourseDetails, JButton addCourse) {
        viewCourseDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCourseDetails((String) courseDisplay.getSelectedValue());
            }
        });
        addCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CourseList cl = null;
                for (CourseList courseList : courseHome.getCourseLists()) {
                    if (courseList.getName().equals(name)) {
                        cl = courseList;
                    }
                }
                addCourseToCourseList(cl);
            }
        });
    }

    // EFFECTS: Makes JFrame where you add a course to the courselist
    private void addCourseToCourseList(CourseList courseList) {
        JFrame addingCourse = new JFrame("Add Course to Course List");
        JTextField course = new JTextField(7);
        JButton add = new JButton("Add course to course list!");
        JLabel timeConflictYesNo = new JLabel("Time Conflicts Checked: " + courseList.getTimeAndNameConflicts());
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourseToCourseListHelper(course, courseList, addingCourse);
            }
        });
        addingCourse.add(course);
        addingCourse.add(add);
        addingCourse.add(timeConflictYesNo);
        jframeInit(addingCourse);
    }

    // EFFECTS: adds functionality to the add button in addCourseToCourseList
    private void addCourseToCourseListHelper(JTextField course, CourseList courseList, JFrame addingCourse) {
        Course courseToAdd = null;
        for (Course c : courseHome.getCourses()) {
            if (c.getName().equals(course.getText())) {
                courseToAdd = c;
            }
        }
        if (courseList.getTimeAndNameConflicts()) {
            if (courseList.courseIsNew(courseToAdd) && courseList.noTimeConflict(courseToAdd)) {
                courseList.addCourse(courseToAdd);
            } else {
                JOptionPane.showMessageDialog(null,
                        "This course has a time conflict with another course in the list",
                        "oopsies", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            courseList.addCourse(courseToAdd);
        }
        addingCourse.dispatchEvent(new WindowEvent(addingCourse, WindowEvent.WINDOW_CLOSING));
    }

    // EFFECTS: JFrame which shows details of a selected course
    public void showCourseDetails(String name) {
        JFrame courseListDetails = new JFrame("CourseLists");
        JButton editDetail = new JButton("Edit Detail");
        JPanel panel = new JPanel();
        JSplitPane splitPane = new JSplitPane();
        JList courseDisplay = new JList();
        DefaultListModel listModel = new DefaultListModel();
        getCourseDetails(name, listModel);
        courseDisplay.setModel(listModel);
        editDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passIntoEditCourse(courseDisplay);
            }
        });
        splitPane.setLeftComponent(new JScrollPane(courseDisplay));
        splitPane.setRightComponent(panel);
        courseListDetails.add(splitPane);
        courseListDetails.add(editDetail);
        jframeInit(courseListDetails);
    }

    // EFFECTS: adds the course details to the default list model in showCourseDetails
    private void getCourseDetails(String name, DefaultListModel listModel) {
        Course c = null;
        for (Course course : courseHome.getCourses()) {
            if (course.getName().equals(name)) {
                c = course;
            }
        }
        listModel.addElement("Name: " + c.getName());
        listModel.addElement("Start Time: " + c.getStartTime());
        listModel.addElement("End Time: " + c.getEndTime());
        listModel.addElement("Credits: " + c.getCredits());
        ArrayList<String> days = c.getDays();
        String allDays = "";
        for (int i = 0; i < days.size(); i++) {
            if (i == (days.size() - 1)) {
                allDays += days.get(i);
            } else {
                allDays += days.get(i) + ", ";
            }
        }
        listModel.addElement("Days: " + allDays);
    }

    // EFFECTS: passes information to edit course based on the JList
    private void passIntoEditCourse(JList courseDisplay) {
        String[] split = ((String) courseDisplay.getSelectedValue()).split(": ");
        String name = ((String) courseDisplay.getModel().getElementAt(0)).split(": ")[1];
        if (split[0].equals("Name")) {
            editCourse("name", name);
        } else if (split[0].equals("Start Time")) {
            editCourse("startTime", name);
        } else if (split[0].equals("End Time")) {
            editCourse("endTime", name);
        } else if (split[0].equals("Credits")) {
            editCourse("credits", name);
        } else if (split[0].equals("Days")) {
            editCourse("days", name);
        }
    }

    // EFFECTS: Make JFrame to edit selected course detail
    private void editCourse(String parameter, String courseName) {
        JFrame editDetails = new JFrame();
        JTextField field = new JTextField("Edit details here!",10);
        JButton save = new JButton("Save Changes");
        editDetails.setTitle("CourseLists");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helperEditCourse(parameter, courseName, field, editDetails);
            }
        });
        editDetails.add(field);
        editDetails.add(save);
        jframeInit(editDetails);
    }

    // EFFECTS: Gives the save button proper edit course functionality
    private void helperEditCourse(String parameter, String courseName, JTextField field, JFrame editDetails) {
        Course course = null;
        for (Course c : courseHome.getCourses()) {
            if (c.getName().equals(courseName)) {
                course = c;
            }
        }
        if (Objects.equals(parameter, "name")) {
            course.setName(field.getText());
        } else if (Objects.equals(parameter, "startTime")) {
            course.setStartTime(field.getText());
        } else if (Objects.equals(parameter, "endTime")) {
            course.setEndTime(field.getText());
        } else if (Objects.equals(parameter, "credits")) {
            course.setCredits(Integer.parseInt(field.getText()));
        } else if (Objects.equals(parameter, "days")) {
            String[] split = field.getText().split(", ");
            ArrayList<String> days = new ArrayList<String>();
            days.addAll(Arrays.asList(split));
            course.setDays(days);
        }
        editDetails.dispatchEvent(new WindowEvent(editDetails, WindowEvent.WINDOW_CLOSING));
    }

}
