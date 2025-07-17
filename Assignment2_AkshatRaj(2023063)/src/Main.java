import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
interface IF_User{   // this is interface (user) which will be implemented by USER.
    boolean login(String email,String password);
    void signup();
    void logout();
}
interface IF_CourseManagement{ // this is interface for course_management which will get implemented by Administrator and Student class.
    void AddCourse(Course course);
    void DropCourse(Course course);
}
abstract class User implements IF_User{ // this is our USER class implementing interface (user) and it will behave as parent class for student,professor and administrator class.
    String Email;
    String Password;
    static List<User> userList = new ArrayList<>(); // we are creating global list for storing all users
    public User(String Email,String Password){
        this.Email=Email;
        this.Password=Password;
    }
    @Override
    public void signup(){
        userList.add(this);
        if(this instanceof Student){
            Student.allStudents.add((Student) this);
        }
        System.out.println("User signed up successfully.");
    }
    @Override
    public boolean login(String email,String password){
        try{
            for (User user : userList) {
                if (user.Email.equals(email) && user.Password.equals(password)) {
                    System.out.println("Login successful!");
                    return true;
                }
            }
            throw new InvalidLoginException("Invalid email or password");

        }
        catch(InvalidLoginException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    @Override
    public void logout(){
        System.out.println("User logged out.");
    }
    public static User findUserByEmail(String email){
        try{
            for (User user : userList) {  // finding user from the global list of all user.
                if (user.Email.equals(email)) {
                    return user;
                }
            }
            throw new InvalidLoginException("invalid email");
        }
        catch(InvalidLoginException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Welcome to the IIIT-DELHI ERP System");
            System.out.println("1. Signup as Student");
            System.out.println("2. Signup as Professor");
            System.out.println("3. Signup as Administrator");
            System.out.println("4. Signup as Teaching Assistant");
            System.out.println("5. Login");
            System.out.println("6. Exit");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    signupStudent(sc);
                    break;
                case 2:
                    signupProfessor(sc);
                    break;
                case 3:
                    signupAdministrator(sc);
                    break;
                case 4:
                    signupTA(sc);
                    break;
                case 5:
                    login(sc);
                    break;
                case 6:
                    System.out.println("Exiting the system...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
        sc.close();
    }
    public static void signupTA(Scanner sc) {
        System.out.println("Enter your name:");
        String name = sc.nextLine();
        System.out.println("Enter your student ID: (Roll no.)");
        int studentID = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.println("Enter your email:");
        String email = sc.nextLine();
        System.out.println("Enter your password:");
        String password = sc.nextLine();

        TA ta = new TA(name, studentID, email, password);
        ta.signup();
    }
    // Signup interface for Student
    public static void signupStudent(Scanner sc) {
        System.out.println("Enter your name:");
        String name=sc.nextLine();
        System.out.println("Enter your student ID: (Roll no.)");
        int studentID=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter your email:");
        String email=sc.nextLine();
        System.out.println("Enter your password:");
        String password=sc.nextLine();
        Student student = new Student(name, studentID, email, password);
        student.signup();
    }
    // Signup interface for Professor
    public static void signupProfessor(Scanner sc) {
        System.out.println("Enter your name:");
        String name = sc.nextLine();
        System.out.println("Enter your email:");
        String email = sc.nextLine();
        System.out.println("Enter your password:");
        String password = sc.nextLine();
        Professor professor = new Professor(name, email, password);
        professor.signup();
    }
    // Signup interface for Administrator
    public static void signupAdministrator(Scanner sc) {
        System.out.println("Enter your email:");
        String email = sc.nextLine();
        System.out.println("Enter your password:");
        String password = sc.nextLine();

        Administrator admin = new Administrator(password);
        admin.signup();
    }
    // Login functionality
    public static void login(Scanner sc) throws ParseException {
        System.out.println("Login as:");
        System.out.println("1. Student");
        System.out.println("2. Professor");
        System.out.println("3. Administrator");
        System.out.println("4. Teaching Assistant");
        int roleChoice = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.println("Enter your email:");
        String email = sc.nextLine();
        System.out.println("Enter your password:");
        String password = sc.nextLine();
        // Depending on the role choice, we will search for the user in the list and ensure they are of the correct type
        switch (roleChoice) {
            case 1:
                User studentUser = User.findUserByEmail(email);
                if (studentUser instanceof Student) {
                    // Set the email and password for the student instance
                    if(studentUser.login(email,password)){
                        handleStudentInterface(sc, (Student) studentUser);
                    };
                }
                else{
                    System.out.println("Role mismatched");
                }
                break;
            case 2:
                User professorUser = User.findUserByEmail(email);
                if (professorUser instanceof Professor) {
                    // Set the email and password for the professor instance
                    professorUser.login(email,password); // Call the login method of the Professor class
                    handleProfessorInterface(sc, (Professor) professorUser);
                }
                else{
                    System.out.println("Role mismatched");
                }
                break;
            case 3:
                User adminUser = User.findUserByEmail(email);
                if (adminUser instanceof Administrator) {
                    // Set the email and password for the administrator instance

                    adminUser.login(email,password); // Call the login method of the Administrator class
                    handleAdministratorInterface(sc, (Administrator) adminUser);
                }
                else{
                    System.out.println("Role mismatched");
                }
                break;
            case 4:
                User taUser = User.findUserByEmail(email);
                if (taUser instanceof TA) {
                    // Set the email and password for the TA instance
                    taUser.login(email,password); // Call the login method of the TA class
                    handleTAInterface(sc, (TA) taUser);
                }
                else{
                    System.out.println("Role mismatched");
                }
                break;
            default:
                System.out.println("Invalid option. Please choose a valid role.");
        }
    }

    public static void handleTAInterface(Scanner sc, TA ta) {
        System.out.println("You are logged in as a Teaching Assistant.");
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("TA Menu:");
            System.out.println("1. View Available Courses");
            System.out.println("2. Add Course");
            System.out.println("3. Drop Course");
            System.out.println("4. View Grades");
            System.out.println("5. View Schedule");
            System.out.println("6. Submit Complaint");
            System.out.println("7. View Complaint Status");
            System.out.println("8. Track Academic Progress (SGPA/CGPA)");
            System.out.println("9. Submit Feedback for Completed Course");
            System.out.println("10. View Assigned Course Grades");
            System.out.println("11. Manage Grades for Students");
            System.out.println("12. Logout");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (option) {
                case 1:
                    System.out.println(ta.viewAvailableCourses());
                    break;
                case 2:
                    System.out.println("Enter Course Code to Add:");
                    int addCourseCode = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter Course Title to Add:");
                    String addCourseTitle = sc.nextLine();
                    // Find the course from available courses
                    Course courseToAdd = null;
                    for (Course course : Administrator.courseCatalog) {
                        if (course.courseCode == addCourseCode && course.title.equals(addCourseTitle)) {
                            courseToAdd = course;
                            break;
                        }
                    }
                    if (courseToAdd != null) {
                        ta.AddCourse(courseToAdd);
                    } else {
                        System.out.println("Course not found in the catalog.");
                    }
                    break;
                case 3:
                    System.out.println("Enter Course Code to Drop:");
                    int dropCourseCode = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter Course Title to Drop:");
                    String dropCourseTitle = sc.nextLine();
                    // Find the course in the student's courselist
                    Course courseToDrop = null;
                    for (Course course : ta.courselist) {
                        if (course.courseCode == dropCourseCode && course.title.equals(dropCourseTitle)) {
                            courseToDrop = course;
                            break;
                        }
                    }
                    if (courseToDrop != null) {
                        ta.DropCourse(courseToDrop);
                    } else {
                        System.out.println("Course not found in your enrolled list.");
                    }
                    break;
                case 4:
                    ta.viewGrades();
                    break;
                case 5:
                    System.out.println(ta.viewSchedule());
                    break;
                case 6:
                    // Submit complaint
                    System.out.println("Enter your complaint:");
                    String complaint = sc.nextLine();
                    ta.SubmitComplaint(new Complaint(complaint), complaint);
                    break;
                case 7:
                    // View complaint status
                    if (ta.complainlist.isEmpty()) {
                        System.out.println("No complaints submitted.");
                    } else {
                        System.out.println("Select a complaint to view its status:");
                        for (int i = 0; i < ta.complainlist.size(); i++) {
                            System.out.println((i + 1) + ". " + ta.complainlist.get(i).Description);
                        }
                        int complaintIndex = sc.nextInt() - 1;
                        sc.nextLine();
                        if (complaintIndex >= 0 && complaintIndex < ta.complainlist.size()) {
                            Complaint selectedComplaint = ta.complainlist.get(complaintIndex);
                            System.out.println("Complaint Status: " + ta.viewComplaintStatus(selectedComplaint));
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    }
                    break;
                case 8:
                    ta.trackAcademicProgress();
                    break;
                case 9:
                    // Submit feedback for completed courses
                    if (ta.completedCourses.isEmpty()) {
                        System.out.println("You haven't completed any courses yet.");
                    } else {
                        System.out.println("Select a completed course to submit feedback:");
                        for (int i = 0; i < ta.completedCourses.size(); i++) {
                            System.out.println((i + 1) + ". " + ta.completedCourses.get(i).title);
                        }
                        int courseIndex = sc.nextInt() - 1;
                        sc.nextLine(); // Consume newline
                        if (courseIndex >= 0 && courseIndex < ta.completedCourses.size()) {
                            Course selectedCourse = ta.completedCourses.get(courseIndex);
                            System.out.println("Enter your feedback type (1 for rating, 2 for comment):");
                            int feedbackType = sc.nextInt();
                            sc.nextLine(); // Consume newline
                            if (feedbackType == 1) {
                                System.out.println("Enter a numeric rating (1–5):");
                                int rating = sc.nextInt();
                                sc.nextLine(); // Consume newline
                                ta.submitFeedback(selectedCourse, new Feedback<>(rating));
                            } else if (feedbackType == 2) {
                                System.out.println("Enter your textual feedback:");
                                String comment = sc.nextLine();
                                ta.submitFeedback(selectedCourse, new Feedback<>(comment));
                            } else {
                                System.out.println("Invalid feedback type.");
                            }
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    }
                    break;
                case 10:
                    // View grades for the assigned course
                    ta.viewGradesForStudents();
                    break;
                case 11:
                    // Manage grades for a student in the assigned course
                    System.out.println("Enter Student's email to manage grades:");
                    String studentEmail = sc.nextLine();
                    Student student = (Student) User.findUserByEmail(studentEmail);
                    if (student != null) {
                        System.out.println("Enter the grade to assign:");
                        String grade = sc.nextLine();
                        ta.manageGradesForStudent(student, grade);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 12:
                    ta.logout();
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
    // Handling student functionality
    public static void handleStudentInterface(Scanner sc, Student student) {
        System.out.println("You are logged in as a student.");
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("Student Menu:");
            System.out.println("1. View Available Courses");
            System.out.println("2. Add Course");
            System.out.println("3. Drop Course");
            System.out.println("4. View Grades");
            System.out.println("5. View Schedule");
            System.out.println("6. Submit Complaint");
            System.out.println("7. View Complaint Status");
            System.out.println("8. Track Academic Progress (SGPA/CGPA)");
            System.out.println("9. Submit Feedback for Completed Course"); // New feature for feedback
            System.out.println("10. Logout");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (option) {
                case 1:
                    System.out.println(student.viewAvailableCourses());
                    break;
                case 2:
                    System.out.println("Enter Course Code to Add:");
                    int addCourseCode = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter Course Title to Add:");
                    String addCourseTitle = sc.nextLine();

                    // Find the course from available courses
                    Course courseToAdd = null;
                    for (Course course : Administrator.courseCatalog) {
                        if (course.courseCode == addCourseCode && course.title.equals(addCourseTitle)) {
                            courseToAdd = course;
                            break;
                        }
                    }
                    if (courseToAdd != null) {
                        student.AddCourse(courseToAdd);
                    } else {
                        System.out.println("Course not found in the catalog.");
                    }
                    break;
                case 3:
                    System.out.println("Enter Course Code to Drop:");
                    int dropCourseCode = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter Course Title to Drop:");
                    String dropCourseTitle = sc.nextLine();

                    // Find the course in the student's courselist
                    Course courseToDrop = null;
                    for (Course course : student.courselist) {
                        if (course.courseCode == dropCourseCode && course.title.equals(dropCourseTitle)) {
                            courseToDrop = course;
                            break;
                        }
                    }
                    if (courseToDrop != null) {
                        student.DropCourse(courseToDrop);
                    } else {
                        System.out.println("Course not found in your enrolled list.");
                    }
                    break;
                case 4:
                    student.viewGrades();
                    break;
                case 5:
                    System.out.println(student.viewSchedule());
                    break;
                case 6:
                    // Submit complaint
                    System.out.println("Enter your complaint:");
                    String complaint = sc.nextLine();
                    student.SubmitComplaint(new Complaint(complaint), complaint);
                    break;
                case 7:
                    // View complaint status
                    if (student.complainlist.isEmpty()) {
                        System.out.println("No complaints submitted.");
                    } else {
                        System.out.println("Select a complaint to view its status:");
                        for (int i = 0; i < student.complainlist.size(); i++) {
                            System.out.println((i + 1) + ". " + student.complainlist.get(i).Description);
                        }
                        int complaintIndex = sc.nextInt() - 1;
                        sc.nextLine();
                        if (complaintIndex >= 0 && complaintIndex < student.complainlist.size()) {
                            Complaint selectedComplaint = student.complainlist.get(complaintIndex);
                            System.out.println("Complaint Status: " + student.viewComplaintStatus(selectedComplaint));
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    }
                    break;
                case 8:
                    student.trackAcademicProgress();
                    break;
                case 9:
                    // Submit feedback for completed courses
                    if (student.completedCourses.isEmpty()) {
                        System.out.println("You haven't completed any courses yet.");
                    } else {
                        System.out.println("Select a completed course to submit feedback:");
                        for(int i = 0; i < student.completedCourses.size(); i++){
                            System.out.println((i + 1) + ". " + student.completedCourses.get(i).title);
                        }
                        int courseIndex = sc.nextInt() - 1;
                        sc.nextLine(); // Consume newline
                        if (courseIndex >= 0 && courseIndex < student.completedCourses.size()) {
                            Course selectedCourse = student.completedCourses.get(courseIndex);
                            System.out.println("Enter your feedback type (1 for rating, 2 for comment):");
                            int feedbackType = sc.nextInt();
                            sc.nextLine(); // Consume newline
                            if (feedbackType == 1) {
                                System.out.println("Enter a numeric rating (1–5):");
                                int rating = sc.nextInt();
                                sc.nextLine(); // Consume newline
                                student.submitFeedback(selectedCourse, new Feedback<>(rating));
                            } else if (feedbackType == 2) {
                                System.out.println("Enter your textual feedback:");
                                String comment = sc.nextLine();
                                student.submitFeedback(selectedCourse, new Feedback<>(comment));
                            } else {
                                System.out.println("Invalid feedback type.");
                            }
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    }
                    break;

                case 10:
                    student.logout();
                    loggedIn = false;
                    break;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Handling professor functionality
    public static void handleProfessorInterface(Scanner sc,Professor professor){
        System.out.println("You are logged in as a professor.");
        boolean loggedIn = true;
        while (loggedIn){
            System.out.println("Professor Menu:");
            System.out.println("1. Update Course Details");
            System.out.println("2. View Enrolled Students");
            System.out.println("3. View Course Feedback"); // Feature to view feedback
            System.out.println("4. Assign TA to Course"); // New feature to assign TA
            System.out.println("5. Logout");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (option) {
                case 1:
                    System.out.println("Enter Course Code:");
                    int courseCode = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.println("Enter Course Title:");
                    String courseTitle = sc.nextLine();

                    // Find the course from the professor's coursesTaught list
                    Course courseToUpdate = null;
                    for (Course course : professor.coursesTaught) {
                        if (course.courseCode == courseCode && course.title.equals(courseTitle)) {
                            courseToUpdate = course;
                            break;
                        }
                    }
                    if (courseToUpdate != null) {
                        // Update the course details
                        System.out.println("Enter new Schedule:");
                        String newSchedule = sc.nextLine();
                        System.out.println("Enter Credits:");
                        int credits = sc.nextInt();
                        sc.nextLine(); // Consume newline
                        System.out.println("Enter Enrollment Limit:");
                        int enrollmentLimit = sc.nextInt();
                        sc.nextLine(); // Consume newline
                        System.out.println("Enter Prerequisite:");
                        String prerequisite = sc.nextLine();
                        System.out.println("Enter Location:");
                        String location = sc.nextLine();
                        // now i am Updating the existing course details
                        professor.updateCourseDetails(courseToUpdate, newSchedule, credits, enrollmentLimit, prerequisite, location);
                    } else {
                        System.out.println("Course not found in your list of courses.");
                    }
                    break;
                case 2:
                    professor.viewEnrolledStudentList();
                    break;
                case 3:
                    // Viewing feedback for the professor's courses
                    if (professor.coursesTaught.isEmpty()) {
                        System.out.println("No courses available.");
                    } else {
                        System.out.println("Select a course to view feedback:");
                        for (int i = 0; i < professor.coursesTaught.size(); i++) {
                            System.out.println((i + 1) + ". " + professor.coursesTaught.get(i).title);
                        }
                        int courseIndex = sc.nextInt() - 1;
                        sc.nextLine(); // Consume newline
                        if (courseIndex >= 0 && courseIndex < professor.coursesTaught.size()) {
                            Course selectedCourse = professor.coursesTaught.get(courseIndex);
                            professor.viewFeedback(selectedCourse);  // Call to view feedback for selected course
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    }
                    break;
                case 4:
                    // Assign TA to a course
                    System.out.println("Enter the TA's email:");
                    String taEmail = sc.nextLine();
                    TA ta = (TA) User.findUserByEmail(taEmail); // Find the TA by email
                    if (ta != null) {
                        System.out.println("Select the course to assign TA:");
                        for (int i = 0; i < professor.coursesTaught.size(); i++) {
                            System.out.println((i + 1) + ". " + professor.coursesTaught.get(i).title);
                        }
                        int courseAssignmentIndex = sc.nextInt() - 1;
                        sc.nextLine(); // Consume newline
                        if (courseAssignmentIndex >= 0 && courseAssignmentIndex < professor.coursesTaught.size()) {
                            Course courseToAssign = professor.coursesTaught.get(courseAssignmentIndex);
                            professor.assignTAToCourse(ta, courseToAssign);  // Assign TA to selected course
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    } else {
                        System.out.println("TA not found.");
                    }
                    break;
                case 5:
                    professor.logout();
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
    public static void handleAdministratorInterface(Scanner sc, Administrator admin) throws ParseException {
        System.out.println("You are logged in as an administrator.");
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("Administrator Menu:");
            System.out.println("1. Add Course");
            System.out.println("2. Drop Course");
            System.out.println("3. Assign Grade");
            System.out.println("4. View Complaints");
            System.out.println("5. Update Complaint Status"); // Added option for updating complaint status
            System.out.println("6. Assign Professor to Course");
            System.out.println("7. Logout");
            int option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    // Adding a new course by taking input from the administrator
                    SimpleDateFormat dateformate= new SimpleDateFormat("dd-MM-yyyy");
                    dateformate.setLenient(true);
                    System.out.println("Enter Course Code:");
                    int newCourseCode = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.println("Enter Course Title:");
                    String newCourseTitle = sc.nextLine();
                    System.out.println("Enter Drop Deadline:");
                    Date deadlinedate = dateformate.parse(sc.nextLine());
                    Course newCourse = new Course(newCourseCode,newCourseTitle,deadlinedate);
                    admin.AddCourse(newCourse);
                    break;
                case 2:
                    // Dropping a course by taking course code input from the administrator
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    dateFormat.setLenient(true);
                    System.out.println("Enter Course Code to Drop:");
                    int dropCourseCode = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.println("Enter Course Title to Drop:");
                    String dropCourseTitle = sc.nextLine();
                    // Search for the course in the course catalog
                    Course courseToDrop = null;
                    for (Course course : Administrator.courseCatalog) {
                        if (course.courseCode == dropCourseCode && course.title.equals(dropCourseTitle)) {
                            courseToDrop = course;
                            break;
                        }
                    }
                    // If course is not found
                    if (courseToDrop == null) {
                        System.out.println("Course not found in the catalog.");
                    } else {
                        admin.DropCourse(courseToDrop);
                    }
                    break;
                case 3:
                    // Assigning a grade to a student by taking inputs
                    System.out.println("Enter Student Email:");
                    String studentEmail = sc.nextLine();
                    // Look for the student in the system
                    Student student = (Student) User.findUserByEmail(studentEmail);
                    if (student == null) {
                        System.out.println("Student not found. Please make sure the student is registered.");
                        break;
                    }
                    System.out.println("Enter Course Code for Grade Assignment:");
                    int gradeCourseCode = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter Course Title for Grade Assignment:");
                    String gradeCourseTitle = sc.nextLine();
                    // Finding the course in the course catalog
                    Course gradeCourse = null;
                    for (Course course : Administrator.courseCatalog) {
                        if (course.courseCode == gradeCourseCode && course.title.equals(gradeCourseTitle)) {
                            gradeCourse = course;
                            break;
                        }
                    }
                    if (gradeCourse == null) {
                        System.out.println("Course not found in the catalog.");
                        break;
                    }
                    System.out.println("Enter Grade:");
                    String grade = sc.nextLine();
                    // Allocating the grade to the student if the course is found
                    admin.allocateGrade(student, gradeCourse, grade);
                    break;
                case 4:
                    // View complaints
                    System.out.println(admin.viewComplaint());
                    break;
                case 5:
                    // Updating the status of a complaint
                    System.out.println("Enter the complaint description to resolve:");
                    String complaintDescription = sc.nextLine();
                    // Finding the complaint in the list
                    Complaint complaintToUpdate = null;
                    for (Complaint complaint : Administrator.complainlist) {
                        if (complaint.Description.equals(complaintDescription)) {
                            complaintToUpdate = complaint;
                            break;
                        }
                    }
                    if (complaintToUpdate != null) {
                        admin.updateComplaintStatus(complaintToUpdate); // Updating the status of the complaint
                    } else {
                        System.out.println("Complaint not found.");
                    }
                    break;
                case 6:
                    // Assigning a professor to a course
                    System.out.println("Enter Course Code:");
                    int assignCourseCode = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.println("Enter Course Title:");
                    String assignCourseTitle = sc.nextLine();
                    // Find the course in the catalog
                    Course assignCourse = null;
                    for (Course course : Administrator.courseCatalog) {
                        if (course.courseCode == assignCourseCode && course.title.equals(assignCourseTitle)) {
                            assignCourse = course;
                            break;
                        }
                    }
                    if (assignCourse == null) {
                        System.out.println("Course not found in the catalog.");
                        break;
                    }
                    // Now for assigning a professor
                    System.out.println("Enter Professor Email:");
                    String professorEmail = sc.nextLine();
                    // first we will find the professor by email
                    Professor professor = (Professor) User.findUserByEmail(professorEmail);
                    if (professor == null) {
                        System.out.println("Professor not found. Please ensure the professor is registered.");
                        break;
                    }
                    // Assigning the professor to the course
                    admin.assign_professor_to_Courses(assignCourse, professor);
                    System.out.println("Professor has been assigned.");
                    break;
                case 7:
                    admin.logout();
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}

