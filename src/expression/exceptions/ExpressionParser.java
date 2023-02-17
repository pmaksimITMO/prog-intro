package expression.exceptions;

import expression.*;

import java.util.Map;

public class ExpressionParser implements TripleParser {
    public ExpressionParser() {}

    public static AllExpression parse(final CharSource source) throws ParserException {
        return new Parser(source).parseExpression();
    }

    @Override
    public TripleExpression parse(final String expression) throws ParserException {
        return parse(new StringSource(expression));
    }
    private static class Parser extends BaseParser {
        private final int maxLevel = 4;
        private final Map<String, Integer> BINARY_OPERATION_PRIORITY = Map.of(
                "gcd", 1,
                "lcm", 1,
                "+", 2,
                "-", 2,
                "*", 3,
                "/", 3
        );

        protected Parser(final CharSource source) {
            super(source);
        }

        public AllExpression parseExpression() throws ParserException {
            final AllExpression result = parseOperation(1);
            skipWhitespaces();
            expect(END);
            return result;
        }

        private String operationType = null;

        private AllExpression parseOperation(int level) throws ParserException {
            operationType = null;
            if (level == maxLevel) {
                return parseMostPriorityOperation();
            }
            AllExpression result = parseOperation(level + 1);
            operationType = parseOperationType();
            while (operationType != null && BINARY_OPERATION_PRIORITY.get(operationType) == level) {
                result = makeOperation(result, operationType, parseOperation(level + 1));
                operationType = parseOperationType();
            }
            return result;
        }

        private AllExpression makeOperation(final AllExpression left, final String type, final AllExpression right) {
            AllExpression result;
            switch (type) {
                case "+" -> result = new CheckedAdd(left, right);
                case "-" -> result = new CheckedSubtract(left, right);
                case "*" -> result = new CheckedMultiply(left, right);
                case "/" -> result = new CheckedDivide(left, right);
                case "gcd" -> result = new CheckedGcd(left, right);
                case "lcm" -> result = new CheckedLcm(left, right);
                default -> result = null;
            }
            return result;
        }

        private AllExpression parseMostPriorityOperation() throws ParserException {
            skipWhitespaces();

            if (take('(')) {
                AllExpression result = parseOperation(1);
                expect(')');
                return result;
            }

            if (take('-')) {
                if (between('0', '9')) {
                    return new Const(parseNumber(true));
                }
                return new CheckedNegate(parseMostPriorityOperation());
            } else if (between('0', '9')) {
                return new Const(parseNumber(false));
            } else if (isVariable()) {
                return parseVariable();
            } else if (take('r')) {
                expect("everse");
                checkForException();
                return new CheckedReverse(parseMostPriorityOperation());
            } else if (test('p')) {
                expect("pow10");
                checkForException();
                return new CheckedPowN(parseMostPriorityOperation());
            } else if (test('l')) {
                expect("log10");
                checkForException();
                return new CheckedLogN(parseMostPriorityOperation());
            }
            throw new MismatchException("Invalid input: expected variable, digit or unary operation; found: '"
                    + take() + "' in position " + getErrorPosition());
        }

        private String parseOperationType() throws ParserException {
            if (operationType != null) {
                return operationType;
            }
            skipWhitespaces();
            String result = null;
            if (take('l')) {
                expect("cm");
                result = "lcm";
                checkForException();
            } else if (take('g')) {
                expect("cd");
                result = "gcd";
                checkForException();
            } else if (isOperationType()) {
                result = "" + take();
            }
            skipWhitespaces();
            return result;
        }

        private boolean isCorrectSymbolAfterOperation() {
            return test('(') || Character.isWhitespace(current()) || test('-');
        }

        private void checkForException() throws ParserException {
            if (!isCorrectSymbolAfterOperation()) {
                throw new MismatchException("Invalid input format: expected open bracket, negate or whitespace; found: '"
                        + take() + "' in position " + getErrorPosition());
            }
        }

        private AllExpression parseVariable() throws ParserException {
            char name = take();
            if (eof() || isWhitespace() || isOperationType() || test(')')) {
                return new Variable("" + name);
            }
            throw new InvalidVariableException("Illegal variable name "
                    + take() + " in position " + getErrorPosition());
        }

        private int parseNumber(boolean isNegative) throws ParserException {
            StringBuilder result = new StringBuilder();
            if (isNegative) {
                result.append("-");
            }
            while (!eof() && between('0', '9')) {
                result.append(take());
            }
            if (!eof() && !isValidSymbolAfterNumber()) {
                throw new MismatchException("Invalid input: expected whitespace or close bracket after const in position " + getErrorPosition());
            }
            try {
                return Integer.parseInt(result.toString());
            } catch (NumberFormatException e) {
                throw new ConstOverflowException("Invalid input: overflow const in position " + getErrorPosition());
            }
        }

        private boolean isValidSymbolAfterNumber() {
            return Character.isWhitespace(current()) || test(')') || !Character.isLetter(current());
        }

        private boolean isVariable() {
            return test('x') || test('y') || test('z');
        }

        private boolean isOperationType() {
            return  (test('+') || test('-') || test('*') || test('/'));
        }

        private void skipWhitespaces() {
            while (isWhitespace()) {
                take();
            }
        }
    }
}
