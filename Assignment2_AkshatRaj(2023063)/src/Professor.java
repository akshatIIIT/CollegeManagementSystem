import java.util.*;
import java.util.Map;
class Professor extends User{
    String ProffName;
    ArrayList<Course> coursesTaught = new ArrayList<>();
    public Professor(String ProffName,String Email,String Password){
        super(Email,Password);
        this.ProffName=ProffName;
    }
    public void assignTAToCourse(TA ta, Course course){
        if (coursesTaught.contains(course)){
            ta.assignedCourse = course;
            System.out.println("TA " + ta.Name + " has been assigned to course: " + course.title);
        } else {
            System.out.println("You are not teaching this course.");
        }
    }
    public void viewEnrolledStudentList() {
        System.out.println("Students enrolled in courses taught by " + ProffName + ":");
        for (Course course:coursesTaught) {
            System.out.println("Course:"+course.title);
            boolean foundStudents=false;
            // Checking each student to see whether they are enrolled in this course or not
            for (Student student:Student.allStudents){ // checking from the global list of students
                if(student.courselist.contains(course)){
                    Map<Course, String> studentGrades = Administrator.studentGrades.get(student);
                    String grade = (studentGrades != null) ? studentGrades.get(course) : "No grade assigned";
                    System.out.println("Student Name: " + student.Name + ", ID: " + student.student_ID + ", Grade: " + grade);
                    foundStudents = true;
                }
            }
            // If no students has added this particular course.
            if (!foundStudents) {
                System.out.println(" No students enrolled in this course.");
            }
        }
    }
    public void updateCourseDetails(Course course, String newSchedule,int credit,int enrollmentlimits,String PreRequisite,String location){
        course.setcredits(credit);
        course.setSchedule(newSchedule);
        course.setEnrollment_limits(enrollmentlimits);
        course.setPreRequisite(PreRequisite);
        course.setlocation(location);
        course.proff=this.ProffName;
        System.out.println("Course details "+course.title+" has been updated");
    }
    public void viewFeedback(Course course) {
        // Get the feedback for the course from the global feedback map
        Map<Student, Map<Course, ArrayList<Feedback<?>>>> feedbackMap = Administrator.feedbackMap;
        boolean feedbackFound = false;
        // Iterate over the feedback map to find the feedback for this course
        for (Map.Entry<Student, Map<Course, ArrayList<Feedback<?>>>> entry : feedbackMap.entrySet()) {
            Student student = entry.getKey();
            Map<Course, ArrayList<Feedback<?>>> courseFeedbackMap = entry.getValue();
            if (courseFeedbackMap.containsKey(course)) {
                ArrayList<Feedback<?>> feedbackList = courseFeedbackMap.get(course);
                System.out.println("Feedback for course: " + course.title);
                for (Feedback<?> feedback : feedbackList) {
                    System.out.println("Student: " + student.Name + " - Feedback: " + feedback.getfeedbackData());
                }
                feedbackFound = true;
            }
        }
        if (!feedbackFound) {
            System.out.println("No feedback found for this course.");
        }
    }
}