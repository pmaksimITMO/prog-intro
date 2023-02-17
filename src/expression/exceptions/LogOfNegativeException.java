package expression.exceptions;

public class LogOfNegativeException extends ExpressionException{
    public LogOfNegativeException(String message) {
        super("Log of negative number: " + message);
    }

    public LogOfNegativeException() {
        super("Log of negative number");
    }
}