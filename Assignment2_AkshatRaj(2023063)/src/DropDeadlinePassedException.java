import java.security.MessageDigest;
import java.util.*;
class DropDeadlinePassedException extends Exception {
    private String Message;
    public DropDeadlinePassedException(String message) {
        super(message);
        this.Message= message;
    }

    @Override
    public String getMessage() {
        return this.Message;
    }
}