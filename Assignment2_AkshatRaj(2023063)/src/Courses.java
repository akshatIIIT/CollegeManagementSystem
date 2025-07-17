import java.util.*;

class Course{
    int courseCode;
    String title;
    int EnrollmentCount;
    private int credits;
    public String proff;
    private String location;
    private String Schedule;
    private int Enrollment_limits;
    private String PreRequisite;
    private int semester;
    private Date dropDeadline;
    public Course(int courseCode,String title,Date dropDeadline){
        this.courseCode=courseCode;
        this.title=title;
        this.dropDeadline=dropDeadline;
    }
    public Date getdropDeadline() {
        return this.dropDeadline;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }
    public int getSemester() {
        return this.semester;
    }
    public void setcredits(int credit){
        this.credits=credit;
    }
    public int getcredits(){
        return this.credits;
    }
    public void setSchedule(String day){
        this.Schedule=day;
    }
    public String getSchedule(){
        return this.Schedule;
    }
    public void setEnrollment_limits(int num){
        this.Enrollment_limits=num;
    }
    public int getEnrollment_limits(){
        return this.Enrollment_limits;
    }
    public void setPreRequisite(String prerequisite){
        this.PreRequisite=prerequisite;
    }
    public String getPreRequisite(){
        return this.PreRequisite;
    }
    public void setlocation(String location){
        this.location=location;
    }
    public String getlocation(){
        return this.location;
    }
}