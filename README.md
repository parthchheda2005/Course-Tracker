# Course Tracker
I made a quick demo!
  - https://www.youtube.com/watch?v=dnariIHAMN0


# My Personal Project

## Phase 0

### What will the application do?
The application will store and plan courses. In each course, you can add and remove courses in different lists.
You can compare courses as well.

### Why is this project of interest to you?
This project is to help organise and plan courses better, and it is meant to help
improve my organizational skills and planning skills when courses open up for next term.

### Who will use it?
This project is primarily meant for me, but I hope that it could help all UBC students.

### User Stories
- As a user, I want to create a course.
- As a user, I want to be able to create a list of courses.
- As a user, I want to be able to remove courses from a list of course.
- As a user, I want to be able to edit a course's details.
- As user, I want to be able to choose whether my course lists check for time conflict or not.
- As a user, I want to be able to save my courses if I want to.
- As a user, I want to be able to load my courses if I want to.
- As a user, I want to be able to save my lists of courses if I want to.
- As a user, I want to be able to load my lists of courses if I want to.

An example of text with **bold** and *italic* fonts.

## Instructions for Grader
- You can do the 1st user story by pressing the Course button in the Home tab. From there, you can
  press the new course button. You can fill in the course name, start and end times
  (needs to be HH:MM format based on 24hr clock, ex. 09:00, 21:00 are valid inputs but 10:00 PM is not),
  credits, and days (multiple days must be separated by the string ", ". The input "Monday, Friday" is a valid input
  but "Monday, Friday, " or "Monday and Friday" is not valid). Then you can press the add course button in the frame.
- You can do the 2nd user story by pressing the Course Lists button in the Home tab. You can then press the New Course
  List button, fill in the information and then press the Make New Course List! button.
- You can do the 3rd user story by pressing the Course Lists button in the Home tab. From there, select a CourseList
  from the JList and press View Course List. From there, you will be redirected to a new tab. In that tab, select a
  course and then press the Remove Course button. You can press the refresh button to see the course list without
  the course you just removed.
- You can do the 4th user story by pressing the Course button in the Home tab. Select a course from the JList, and then
  press the view course button. You will be redirected to a frame with the course's details. From there, select a detail
  the JList showing the course's details and press Edit Detail. From there, fill in the detail that you want to change.
  Please ensure for time that you are using a 24hr HH:MM format, and editing days has the same rules as setting days in a
  new course.
- The 5th user story is shown through the JComboBox asking to consider time conflicts in the Course List. While adding a
  course to the course list, there is also a JLabel stating whether the courseList considers time conflicts.
-  User Stories 6 and 7 can be done in the Home tab. There is a save button there, and all changes made to the work
   state (Courses and Course Lists) will be saved to a json file.
- User Stories 8 and 9 can be done in the Home tab. There is a load button which load all data saved in a json file into
  the app.


### Images cited
““Refresh” Icon - Download for Free.” Iconduck, iconduck.com/icons/257090/refresh. Accessed 25 Mar. 2024.
“Study Cartoon - Unlimited Download. Cleanpng.com.” Cleanpng.com,
www.cleanpng.com/png-study-skills-learning-student-test-clip-art-cartoo-3962889/. Accessed 25 Mar. 2024.

## Phase 4: Task 2
Representative sample of events that occur when program runs:

New course CPSC210 was created

New course BIOL111 was created

New course LING100 was created

New course DSCI100 was created

New CourseList demo was created, checks for time conflicts is false.

CPSC210 was added to demo

BIOL111 was added to demo

LING100 was added to demo

New CourseList demoWithTimeConflict was created, checks for time conflicts is true.

CPSC210 was added to demoWithTimeConflict

LING100 was added to demoWithTimeConflict

DSCI100 was added to demoWithTimeConflict

## Phase 4: Task 3
I believe that I should have divided by GUI across multiple classes. Coupling is really high,
which terrible since each class should ideally have one single responsibility. Changing variable names would cause
errors in other parts of the program that would be challenging to find. There are also a lot of helper functions for
each GUI, which aren't descriptively named. I would need to heavily rely on the effects clauses to figure out what
function does what. I should have used the @SupressWarning on a lot of my methods, since the helper methods are not
usable elsewhere in the class due to the specific parameters.
