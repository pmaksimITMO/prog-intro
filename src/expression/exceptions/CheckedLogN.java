package expression.exceptions;

import expression.AllExpression;
import expression.UnaryOperation;

public class CheckedLogN extends UnaryOperation {
    public CheckedLogN(AllExpression value) {
        super(value);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    protected String getType() {
        return "log10";
    }

    @Override
    protected int calculate(int value) {
        if (value <= 0) {
            throw new LogOfNegativeException(getType() + " " + value);
        }
        int result = 0;
        while (value != 0) {
            result++;
            value /= 10;
        }
        return result - 1;
    }

    @Override
    protected double calculate(double value) {
        return calculate((int) value);
    }
}
