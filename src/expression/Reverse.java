package expression;

public class Reverse extends UnaryOperation{
    public Reverse(AllExpression value) {
        super(value);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    protected String getType() {
        return "reverse";
    }

    @Override
    protected int calculate(int value) {
        int result = 0;
        while (value != 0) {
            result = (result * 10 + (value % 10));
            value /= 10;
        }
        return result;
    }

    @Override
    protected double calculate(double value) {
        throw new IllegalArgumentException("Unsupported type: double");
    }
}
