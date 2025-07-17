import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
class Administrator extends User implements IF_CourseManagement {
    static ArrayList<Course> courseCatalog = new ArrayList<>();
    static Map<Student, Map<Course, String>> studentGrades = new HashMap<>();
    static ArrayList<Complaint> complainlist = new ArrayList<>();
    static Map<Student, Map<Course, ArrayList<Feedback<?>>>> feedbackMap = new HashMap<>();

    public Administrator( String Password) {
        super("Admin", Password);  // I am keeping the Email and Password same for all administrator as written in assignment.
    }
    public void AddCourse(Course course){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the semester for this course: ");
        int semester = sc.nextInt();
        course.setSemester(semester);  // for setting the semester for the course
        // we are adding the course to the course catalog
        courseCatalog.add(course);
        System.out.println("Course added: "+course.title+" for semester " +semester);
    }
    public void DropCourse(Course course){
        if (courseCatalog.contains(course)){
            courseCatalog.remove(course);
            System.out.println("Course removed" + course.title);
        }
        else{
            System.out.println("Course not found");
        }
    }
    public void assign_professor_to_Courses(Course course, Professor professor) {
        course.proff = professor.ProffName;
        professor.coursesTaught.add(course); // Adding the course to the professor's list of courses
        System.out.println("Professor " + professor.ProffName + " assigned to course: " + course.title);
    }
    public void allocateGrade(Student student, Course course, String grade) {
        if (!studentGrades.containsKey(student)) {
            studentGrades.put(student, new HashMap<>());
        }
        // Assigning the grade to the student for the course
        studentGrades.get(student).put(course, grade);
        System.out.println("Grade " + grade + " assigned for course " + course.title + " to student " + student.Name);
        // If the student passes the course (grade is not "F"), we will mark it as completed
        if (!grade.equals("F")) {
            // Avoid duplicate entries by checking if the course is already marked as completed
            if (!student.completedCourses.contains(course)) {
                student.completedCourses.add(course);  // Adding the course to completed courses list
                System.out.println("Course " + course.title + " has been marked as completed for student " + student.Name);
            }
        }
    }
    public String viewComplaint() {
        StringBuilder allComplaints = new StringBuilder();
        for (Complaint complaint : complainlist) {
            allComplaints.append("Complain:").append(complaint.Description).append("-Status:").append(complaint.getStatus()).append("\n");
        }
        return allComplaints.toString();
    }
    public void updateComplaintStatus(Complaint complaint) {
        complaint.setStatus();
        System.out.println(" Your Complaint resolved" + complaint.Description);
    }
}