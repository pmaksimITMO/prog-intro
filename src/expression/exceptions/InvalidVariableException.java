package expression.exceptions;

public class InvalidVariableException extends ParserException {
    public InvalidVariableException(String message) {
        super(message);
    }

    public InvalidVariableException() {
        super("Invalid variable exception");
    }
}
