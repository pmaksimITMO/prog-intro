package expression;

public class Gcd extends BinaryOperation{
    public Gcd(AllExpression left, AllExpression right) {
        super(left, right);
    }

    private int getGCD(int x, int y) {
        if (y == 0) {
            return Math.abs(x);
        }
        return Math.abs(getGCD(y, x % y));
    }

    @Override
    protected int calculate(int l, int r) {
        return getGCD(l, r);
    }

    @Override
    protected double calculate(double l, double r) {
        throw new IllegalArgumentException("GCD is not support double calculation");
    }

    @Override
    public int getPriority() {
        return 400;
    }

    @Override
    protected String getType() {
        return "gcd";
    }

    @Override
    protected boolean isAssociative() {
        return true;
    }

    @Override
    protected boolean isContinuous() {
        return false;
    }
}
