package expression.exceptions;

import expression.Add;
import expression.AllExpression;

public class CheckedAdd extends Add {
    public CheckedAdd(AllExpression left, AllExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int l, int r) {
        if (hasError(l, r)) {
            throw new OverflowExpressionException(l + " " + getType() + " " + r);
        }
        return l + r;
    }

    public static boolean hasError(int l, int r) {
        return (l > 0 && Integer.MAX_VALUE - l < r || l < 0 && Integer.MIN_VALUE - l > r);
    }
}
