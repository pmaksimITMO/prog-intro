package expression;

public class Add extends BinaryOperation {

    public Add(AllExpression left, AllExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int l, int r) {
        return l + r;
    }

    @Override
    protected double calculate(double l, double r) {
        return l + r;
    }

    @Override
    public int getPriority() {
        return 200;
    }

    @Override
    protected String getType() {
        return "+";
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
