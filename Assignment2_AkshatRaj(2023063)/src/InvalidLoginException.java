import java.util.*;
class InvalidLoginException extends Exception {
    private String Message;
    public InvalidLoginException(String message) {
        super(message);
        this.Message=message;
    }
    public String getMessage(){
        return this.Message;
    }

}