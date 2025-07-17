import java.util.*;
class CourseFullException extends Exception {
    private String Message;
    public CourseFullException(String message) {
        super(message);
        this.Message=message;
    }
    public String getMessage(){
        return this.Message;
    }

}