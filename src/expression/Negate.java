package expression;

public class Negate extends UnaryOperation {

    public Negate(AllExpression value) {
        super(value);
    }

    @Override
    protected String getType() {
        return "-";
    }

    @Override
    protected int calculate(int x) {
        return -x;
    }

    @Override
    protected double calculate(double x) {
        return -x;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
