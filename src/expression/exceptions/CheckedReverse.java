package expression.exceptions;

import expression.AllExpression;
import expression.Reverse;

public class CheckedReverse extends Reverse {
    public CheckedReverse(AllExpression value) {
        super(value);
    }

    @Override
    protected int calculate(int value) {
        int result = 0;
        while (value != 0) {
            if (CheckedMultiply.hasError(result, 10) || CheckedAdd.hasError(10 * result, value % 10)) {
                throw new OverflowExpressionException(getType() + " " + value);
            }
            result = (result * 10 + (value % 10));
            value /= 10;
        }
        return result;
    }
}
