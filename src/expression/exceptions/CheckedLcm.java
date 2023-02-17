package expression.exceptions;

import expression.AllExpression;
import expression.Lcm;

public class CheckedLcm extends Lcm {
    public CheckedLcm(AllExpression left, AllExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int l, int r) {
        if (l == 0 && r == 0) {
            return 0;
        }
        if (hasError(l, r)) {
            throw new OverflowExpressionException(l + " " + getType() + " " + r);
        }
        return l / CheckedGcd.gcd(l, r) * r;
    }

    private static boolean hasError(int l, int r) {
        return CheckedMultiply.hasError(l / CheckedGcd.gcd(l, r), r) && CheckedGcd.gcd(l, r) != 0;
    }
}
