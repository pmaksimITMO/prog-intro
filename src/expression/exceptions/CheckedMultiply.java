package expression.exceptions;

import expression.AllExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(AllExpression left, AllExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int l, int r) {
        if (hasError(l, r)) {
            throw new OverflowExpressionException(l + " " + getType() + " " + r);
        }
        return l * r;
    }

    public static boolean hasError(int l, int r) {
        if (l < 0) {
            return r < 0 && Integer.MAX_VALUE / r > l || r > 0 && Integer.MIN_VALUE / r > l;
        } else if (l > 0) {
            return r < 0 && Integer.MIN_VALUE / l > r || r > 0 && Integer.MAX_VALUE / l < r;
        }
        return false;
    }
}
