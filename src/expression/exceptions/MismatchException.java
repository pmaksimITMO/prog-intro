package expression.exceptions;

public class MismatchException extends ParserException {
    public MismatchException(String message) {
        super(message);
    }

    public MismatchException() {
        super("Mismatch exception");
    }
}
