package expression.exceptions;

import expression.AllExpression;
import expression.UnaryOperation;

public class CheckedPowN extends UnaryOperation {
    public CheckedPowN(AllExpression value) {
        super(value);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    protected String getType() {
        return "pow10";
    }

    @Override
    protected int calculate(int value) {
        if (value < 0) {
            throw new NotAnIntegerValueException(getType() + " " + value);
        }
        int result = 1;
        for (int i = 0; i < value; i++) {
            if (CheckedMultiply.hasError(result, 10)) {
                throw new OverflowExpressionException(getType() + " " + value);
            }
            result *= 10;
        }
        return result;
    }

    @Override
    protected double calculate(double value) {
        return calculate((int) value);
    }
}
