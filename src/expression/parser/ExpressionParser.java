package expression.parser;

import expression.*;

import java.util.Map;

public final class ExpressionParser implements TripleParser {
    public ExpressionParser() {}

    public static AllExpression parse(final CharSource source) {
        return new Parser(source).parseExpression();
    }

    @Override
    public TripleExpression parse(final String expression) {
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

        public AllExpression parseExpression() {
            final AllExpression result = parseOperation(1);
            if (eof()) {
                return result;
            }
            throw error("End of expression expected");
        }

        private String operationType = null;

        private AllExpression parseOperation(int level) {
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
                case "+" -> result = new Add(left, right);
                case "-" -> result = new Subtract(left, right);
                case "*" -> result = new Multiply(left, right);
                case "/" -> result = new Divide(left, right);
                case "gcd" -> result = new Gcd(left, right);
                case "lcm" -> result = new Lcm(left, right);
                default -> result = null;
            }
            return result;
        }

        private AllExpression parseMostPriorityOperation() {
            skipWhitespaces();

            if (take('(')) {
                AllExpression result = parseOperation(1);
                expect(')');
                return result;
            }

            if (take('-')) {
                if (between('0', '9')) {
                    return new Const(-parseNumber());
                }
                return new Negate(parseMostPriorityOperation());
            } else if (between('0', '9')) {
                return new Const(parseNumber());
            } else if (isVariable()) {
                return parseVariable();
            } else if (take('r')) {
                expect("everse");
                return new Reverse(parseMostPriorityOperation());
            }
            throw error("Invalid input: expected variable, digit or negate; found: " + take());
        }

        private String parseOperationType() {
            if (operationType != null) {
                return operationType;
            }
            skipWhitespaces();
            String result = null;
            if (take('l')) {
                expect("cm");
                result = "lcm";
            } else if (take('g')) {
                expect("cd");
                result = "gcd";
            }else if (isOperationType()) {
                result = "" + take();
            }
            skipWhitespaces();
            return result;
        }

        private AllExpression parseVariable() {
            char name = take();
            if (eof() || isWhitespace() || isOperationType() || test(')')) {
                return new Variable("" + name);
            }
            throw error("Illegal variable name");
        }

        private int parseNumber() {
            StringBuilder result = new StringBuilder();
            while (!eof() && between('0', '9')) {
                result.append(take());
            }
            try {
                return Integer.parseUnsignedInt(result.toString());
            } catch (NumberFormatException e) {
                throw error("Invalid input: overflow const " + e.getMessage());
            }
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
