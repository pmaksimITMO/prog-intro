package expression;

import java.util.Objects;

public abstract class BinaryOperation implements AllExpression {
    private final AllExpression left, right;

    protected abstract String getType();

    protected abstract int calculate(int l, int r);

    protected abstract double calculate(double l, double r);

    protected abstract boolean isAssociative();

    protected abstract boolean isContinuous();

    public BinaryOperation(AllExpression left, AllExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate(double x) {
        return calculate(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public int evaluate(int x) {
        return calculate(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public String toMiniString() {
        return makeMiniString(left, true) + " " + getType() + " " + makeMiniString(right, false);
    }

    private String makeMiniString(AllExpression item, boolean left) {
        final int nowPriority = this.getPriority();
        final int exPriority = item.getPriority();

        if (item instanceof BinaryOperation) {
            BinaryOperation operation = (BinaryOperation) item;
            final String exType = operation.getType();

            if (nowPriority < exPriority) {
                return "(" + item.toMiniString() + ")";
            } else if (nowPriority == exPriority && !left) {
                if (getType().equals(exType)) {
                    String result = item.toMiniString();
                    if (isAssociative()) {
                        return result;
                    }
                    return "(" + result + ")";
                }
                return (isAssociative() && operation.isContinuous()) ? item.toMiniString() : "(" + item.toMiniString() + ")";
            }
        }
        return item.toMiniString();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + getType() + " " + right.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryOperation) {
            BinaryOperation it = (BinaryOperation) obj;
            return Objects.equals(left, it.left) && Objects.equals(right, it.right)
                    && Objects.equals(getType(), it.getType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left.hashCode(), right.hashCode(), this.getClass());
    }
}
