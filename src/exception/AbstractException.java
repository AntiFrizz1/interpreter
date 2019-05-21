package exception;

public class AbstractException extends Exception {
    public AbstractException (String message, String context, int line) {
        super(message + " " + context + ":" + line);
    }
}
