package expression.exceptions;

import expression.AllExpression;
import expression.Divide;

public class CheckedDivide extends Divide {
    public CheckedDivide(AllExpression left, AllExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int l, int r) {
        if (r == 0) {
            throw new DivideByZeroException(l + " " + getType() + " " + r);
        }
        if (r == -1 && l == Integer.MIN_VALUE) {
            throw new OverflowExpressionException(l + " " + getType() + " " + r);
        }
        return l / r;
    }
}
