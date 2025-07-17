import java.util.*;
import java.util.HashMap;
import java.util.Map;
class Student extends User implements IF_CourseManagement{ // this is
    String Name;
    int student_ID; // ROLL NUMBER
    int semester=1;
    String Grades;
    ArrayList<Course> courselist=new ArrayList<>(); // we will add and remove the courses selected by the students in this list.
    ArrayList<Complaint> complainlist=new ArrayList<>(); // we will submit all the complaints in this array as a form of object.
    static ArrayList<Student> allStudents = new ArrayList<>();
    ArrayList<Double> semesterGPA = new ArrayList<>();
    ArrayList<Course> completedCourses = new ArrayList<>(); // List to track completed courses
    Map<Course,ArrayList<Feedback<?>>> feedbackMap=new HashMap<>(); // keeping feedback for every courses.

    public Student(String Name,int student_ID,String Email,String Password){
        super(Email,Password);
        this.Name=Name;
        this.student_ID=student_ID;
    };
    public void AddCourse(Course course) {
        try{
            // Checking if the course is for the student's current semester
            if(course.getSemester()!=this.semester){
                System.out.println("You cannot register for this course as it is not for your semester.");
                return;
            }
            // Checking if the total credits exceed 20
            int totalCredits=0;
            for(Course c : courselist){
                totalCredits += c.getcredits();
            }
            if(totalCredits + course.getcredits()>20){
                System.out.println("You cannot add this course as the total credit limit of 20 will be exceeded.");
                return;
            }

            // Checking if the course is full
            if(course.EnrollmentCount >= course.getEnrollment_limits()){
                throw new CourseFullException("enrollment limit is full. You cannot register for this course.");
            }

            // Checking if the prerequisite for the course is met
            if(!completedPreRequisite(course)){
                return;
            }
            // Adding the course to the courselist and updating enrollment count
            courselist.add(course);
            course.EnrollmentCount++; // Assuming you have this method in Course class
            System.out.println("Course added: " + course.title);
        } catch (CourseFullException e){
            System.out.println(e.getMessage());
        }
    }

    public void DropCourse(Course course) {
        try {
            Date currentDate = new Date();
            // Checking if the current date is after the drop deadline
            if (currentDate.after(course.getdropDeadline())){
                throw new DropDeadlinePassedException("You cannot drop this course. The drop deadline has passed.");
            }
            // we will drop the course if deadline has not passed
            if (courselist.contains(course)) {
                courselist.remove(course);
                System.out.println("Course removed: " + course.title);
            } else {
                System.out.println("Course not found in your list.");
            }
        } catch (DropDeadlinePassedException e) {
            System.out.println(e.getMessage());
        }
    }
    private boolean completedPreRequisite(Course course) {
        // we will Skip prerequisite check for semester 1 since semester 1 does not have any preRequisite
        if (this.semester == 1) {
            return true;
        }
        // Skiping prerequisite check if the course has no prerequisites
        if (course.getPreRequisite().isEmpty()) {
            return true;
        }
        String preRequisite = course.getPreRequisite();
        // we are Searching in the student's completed courses for the prerequisite
        Map<Course, String> grades = Administrator.studentGrades.get(this);
        for (Course completedCourse : completedCourses){
            if (completedCourse.title.equals(preRequisite)){  // Finding the prerequisite course
                String grade = grades.get(completedCourse);
                if (grade != null && !grade.equals("F")){  // if the grade is F means student has failed
                    return true;
                }
            }
        }
        System.out.println("You cannot register for this course as the prerequisite " + preRequisite + " has not been completed or was failed.");
        return false;
    }
    public ArrayList<Course> viewAvailableCourses() {
        StringBuilder availableCourses = new StringBuilder();
        availableCourses.append("Available Courses for Semester ").append(semester).append(":\n");
        // Filtering courses based on the student's current semester
        ArrayList<Course> filteredCourses = new ArrayList<>();
        if (Administrator.courseCatalog.isEmpty()) {
            availableCourses.append("No courses available.\n");
        } else {
            for (Course course : Administrator.courseCatalog) {
                if (course.getSemester() == this.semester) {  // Show only courses for the student's current semester
                    filteredCourses.add(course);  // Add to the filtered list
                    availableCourses.append("Course Code: ").append(course.courseCode)
                            .append("\nTitle: ").append(course.title)
                            .append("\nProfessor: ").append(course.proff)
                            .append("\nCredits: ").append(course.getcredits())
                            .append("\nPrerequisites: ").append(course.getPreRequisite())
                            .append("\nTimings: ").append(course.getSchedule())
                            .append("\n\n");
                }
            }
            if (filteredCourses.isEmpty()) {
                availableCourses.append("No courses available for your semester.\n");
            }
        }
        System.out.println(availableCourses.toString());
        return filteredCourses;  // Returning only the filtered list of courses for that semester
    }
    public void viewGrades(){
        System.out.println("Grades for "+ Name+ ":");
        Map<Course,String> grades=Administrator.studentGrades.get(this);
        if(grades!=null){
            for(Course course:courselist){
                if(grades.containsKey(course)){
                    System.out.println(course.title+":"+grades.get(course));
                }
                else{
                    System.out.println(course.title+":No grade assigned yet");
                }
            }
        }
    }
    public String viewSchedule(){
        StringBuilder schedule = new StringBuilder();
        schedule.append("Weekly Schedule for ").append(Name).append("\n");
        if (courselist.isEmpty()) {
            schedule.append("No courses enrolled.\n");
        } else {
            for (Course course : courselist) {
                schedule.append("Course: ").append(course.title)
                        .append("\nTimings: ").append(course.getSchedule())
                        .append("\nProfessor: ").append(course.proff)
                        .append("\nLocation: ").append(course.getlocation())
                        .append("\n\n");
            }
        }
        return schedule.toString();
    }
    public void SubmitComplaint(Complaint complaint,String complaining){
        complaint.Description=complaining;
        complainlist.add(complaint);
        Administrator.complainlist.add(complaint);
        System.out.println("Complaint submitted:"+complaining);
    }
    public String viewComplaintStatus(Complaint complaint){
        return complaint.getStatus();
    }
    private double gradeToPoints(String grade) {
        switch(grade){
            case "A": return 10.0;
            case "A-": return 9.0;
            case "B": return 8.0;
            case "B-": return 7.0;
            case "C": return 6.0;
            case "C-": return 5.0;
            case "D": return 4.0;
            case "F": return 0.0;
            default: return 0.0;
        }
    }
    public double calculateSGPA() {
        double totalPoints = 0;
        int totalCredits = 0;
        Map<Course, String> grades = Administrator.studentGrades.get(this);
        if (grades != null){
            for (Course course : courselist){
                String grade = grades.get(course);
                if (grade != null){
                    double gradePoints = gradeToPoints(grade);
                    totalPoints += gradePoints * course.getcredits();
                    totalCredits += course.getcredits();
                }
            }
        }
        if (totalCredits == 0){
            System.out.println("No grades available to calculate SGPA.");
            return 0.0;
        }
        return totalPoints / totalCredits;
    }
    public double calculateCGPA(ArrayList<Double> semesterGPA){
        if (semesterGPA.isEmpty()) {
            System.out.println("No completed semesters to calculate CGPA.");
            return 0.0;
        }
        double totalGPA=0.0;
        for (double sgpa : semesterGPA) {
            totalGPA+=sgpa;
        }
        return totalGPA/semesterGPA.size();
    }
    public void progressToNextSemester() {
        int totalCredits = 0;
        for (Course course : courselist) {
            totalCredits += course.getcredits();
        }
        if (totalCredits >= 20) {
            semester++;
            courselist.clear();  // Clear the course list for the new semester
            System.out.println("You have completed this semester. Welcome to semester " + semester + "!");
        }
    }
    public void trackAcademicProgress() {
        // we will Calculate SGPA for current semester
        double sgpa = calculateSGPA();
        if (sgpa > 0) {
            semesterGPA.add(sgpa);  // Store SGPA for the completed semester
            System.out.println("Your SGPA for semester " + semester + " is: " + sgpa);
        }
        // we will Calculate CGPA across all semesters
        double cgpa = calculateCGPA(semesterGPA);
        System.out.println("Your CGPA across all semesters is: " + cgpa);
        // now promoting the student to next semester
        progressToNextSemester();
    }
    public void submitFeedback(Course course, Feedback<?> feedback) {
        if (!Administrator.feedbackMap.containsKey(this)) {
            Administrator.feedbackMap.put(this, new HashMap<>());
        }
        Map<Course, ArrayList<Feedback<?>>> courseFeedbackMap = Administrator.feedbackMap.get(this);
        if (!courseFeedbackMap.containsKey(course)) {
            courseFeedbackMap.put(course, new ArrayList<>());
        }
        courseFeedbackMap.get(course).add(feedback);
        System.out.println("Feedback submitted for course: " + course.title);
    }


}