import java.util.*;

class TA extends Student {
    public Course assignedCourse;
    public TA(String Name, int student_ID, String Email, String Password) {
        super(Name, student_ID, Email, Password);
        this.semester = 5; // TA must be above semester 4 or higher
    }
    // Method to view grades of all students for the assigned course
    public void viewGradesForStudents() {
        if (assignedCourse == null) {
            System.out.println("You are not assigned to any course.");
            return;
        }
        System.out.println("Viewing grades for students in course: " + assignedCourse.title);
        boolean studentsFound = false;
        // Iterate through all students and view their grades in the given course
        for (Student student : Student.allStudents) {
            Map<Course, String> grades = Administrator.studentGrades.get(student);
            if (grades != null && grades.containsKey(assignedCourse)) {
                String grade = grades.get(assignedCourse);
                System.out.println("Student: " + student.Name + ", Grade: " + grade);
                studentsFound = true;
            }
        }
        if (!studentsFound) {
            System.out.println("No students have been graded for this course yet.");
        }
    }
    // Method to directly assign or update grades for students in a course
    public void manageGradesForStudent(Student student, String grade) {
        // Check if the student is enrolled in the assigned course
        if (!student.courselist.contains(assignedCourse)) {
            System.out.println("Error:student " + student.Name + " is not enrolled in the course " + assignedCourse.title + ".");
            return;
        }
        // Proceed with assigning the grade since the student is enrolled in the course
        System.out.println("Managing grade for student: " + student.Name + " in course: " + assignedCourse.title);
        // Allocating the grade directly
        if (!Administrator.studentGrades.containsKey(student)) {
            Administrator.studentGrades.put(student, new HashMap<>());
        }
        // Updating or Adding the student's grade for the assigned course
        Administrator.studentGrades.get(student).put(assignedCourse, grade);
        // If the grade is not "F", the course is marked as completed
        if (!grade.equals("F")) {
            if (!student.completedCourses.contains(assignedCourse)) {
                student.completedCourses.add(assignedCourse);
            }
            System.out.println("Grade " + grade + " assigned and course " + assignedCourse.title + " marked as completed.");
        } else {
            System.out.println("Grade " + grade + " assigned, but course not marked as completed.");
        }
    }
}
