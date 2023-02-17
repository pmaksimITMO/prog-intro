package expression.exceptions;

public class NotAnIntegerValueException extends ExpressionException {
    public NotAnIntegerValueException(String message) {
        super("Not an integer value " + message);
    }

    public NotAnIntegerValueException() {
        super("Not an integer value");
    }
}
