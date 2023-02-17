package expression;

public class Multiply extends BinaryOperation {
    public Multiply(AllExpression left, AllExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int l, int r) {
        return l * r;
    }

    @Override
    protected double calculate(double l, double r) {
        return l * r;
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    protected String getType() {
        return "*";
    }

    @Override
    protected boolean isAssociative() {
        return true;
    }

    @Override
    protected boolean isContinuous() {
        return true;
    }
}
