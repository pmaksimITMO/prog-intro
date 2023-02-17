package expression.exceptions;

public class ConstOverflowException extends ParserException {
    public ConstOverflowException(String message) {
        super(message);
    }

    public ConstOverflowException() {
        super("Const is overflow");
    }
}
