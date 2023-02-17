package expression.exceptions;

public class OverflowExpressionException extends ExpressionException {
    public OverflowExpressionException(String expression) {
        super(expression + " caused exception");
    }
    public OverflowExpressionException() {
        super("Overflow exception");
    }
}
