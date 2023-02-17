package expression;

public class Lcm extends BinaryOperation{
    public Lcm(AllExpression left, AllExpression right) {
        super(left, right);
    }

    private int getGCD(int x, int y) {
        if (y == 0) {
            return Math.abs(x);
        }
        return Math.abs(getGCD(y, x % y));
    }

    private int getLCM(int x, int y) {
        int gcd = getGCD(x, y);
        if (gcd == 0) {
            return 0;
        }
        return x /gcd * y;
    }

    @Override
    public int getPriority() {
        return 400;
    }

    @Override
    protected String getType() {
        return "lcm";
    }

    @Override
    protected int calculate(int l, int r) {
        return getLCM(l, r);
    }

    @Override
    protected double calculate(double l, double r) {
        throw new IllegalArgumentException("LCM is not support double calculation");
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
