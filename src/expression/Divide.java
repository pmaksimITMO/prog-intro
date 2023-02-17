package expression;

public class Divide extends BinaryOperation {
    public Divide(AllExpression left, AllExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int l, int r) {
        return l/r;
    }

    @Override
    protected double calculate(double l, double r) {
        return l/r;
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    protected String getType() {
        return "/";
    }

    @Override
    protected boolean isAssociative() {
        return false;
    }

    @Override
    protected boolean isContinuous() {
        return false;
    }
}
