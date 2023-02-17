package expression.exceptions;

import expression.AllExpression;
import expression.Negate;

public class CheckedNegate extends Negate {
    public CheckedNegate(AllExpression value) {
        super(value);
    }

    @Override
    protected int calculate(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowExpressionException(getType() + " " + x);
        }
        return -x;
    }
}
