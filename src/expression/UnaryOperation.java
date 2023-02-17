package expression;

import java.util.Objects;

public abstract class UnaryOperation implements AllExpression{
    private final AllExpression value;

    protected abstract String getType();

    protected abstract int calculate(int value);
    protected abstract double calculate(double value);

    public UnaryOperation(AllExpression value) {
        this.value = value;
    }

    @Override
    public double evaluate(double x) {
        return calculate(value.evaluate(x));
    }

    @Override
    public int evaluate(int x) {
        return calculate(value.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(value.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return getType() + "(" + value.toString() + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.hashCode(), this.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnaryOperation) {
            UnaryOperation it = (UnaryOperation) obj;
            return Objects.equals(value, it.value) && Objects.equals(getType(), it.getType());
        }
        return false;
    }

    @Override
    public String toMiniString() {
        if (value instanceof BinaryOperation) {
            return getType() + "(" + value.toMiniString() + ")";
        }
        return getType() + " " + value.toMiniString();
    }
}
