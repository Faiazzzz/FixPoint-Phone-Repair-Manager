package exception;

public class InvalidIssueTypeException extends Exception {
    public InvalidIssueTypeException(String message) {
        super(message);
    }
}