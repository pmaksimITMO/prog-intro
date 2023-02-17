package expression.exceptions;

import expression.AllExpression;
import expression.Gcd;

public class CheckedGcd extends Gcd {
    public CheckedGcd(AllExpression left, AllExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int l, int r) {
        try {
            gcd(l, r);
        } catch (Exception e) {
            throw new OverflowExpressionException(l + " " + getType() + " " + r);
        }
        return gcd(l, r);
    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return abs(a);
        }
        return abs(gcd(b, a % b));
    }

    private static int abs(int x) {
        return x > 0 ? x : -x;
    }
}
