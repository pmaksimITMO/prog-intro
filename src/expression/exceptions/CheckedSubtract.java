package expression.exceptions;

import expression.AllExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(AllExpression left, AllExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int l, int r) {
        if (hasError(l, r)) {
            throw new OverflowExpressionException(l + " " + getType() + " " + r);
        }
        return l - r;
    }

    private static boolean hasError(int l, int r) {
        return r > 0 && Integer.MIN_VALUE + r > l || r < 0 && Integer.MAX_VALUE + r < l;
    }
}
