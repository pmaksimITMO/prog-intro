package expression.exceptions;

public class DivideByZeroException extends ExpressionException {
    public DivideByZeroException(String expression) {
        super("Division by zero: " + expression);
    }

    public DivideByZeroException() {
        super("Division by zero");
    }
}
