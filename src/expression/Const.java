package expression;

import java.util.Objects;

public class Const implements AllExpression {
    private final Number CONST;

    public Const(int value) {
        this.CONST = value;
    }

    public Const(double value) {
        this.CONST = value;
    }

    @Override
    public double evaluate(double x) {
        return CONST.doubleValue();
    }

    @Override
    public int evaluate(int x) {
        return CONST.intValue();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return CONST.intValue();
    }

    @Override
    public int hashCode() {
        return CONST.hashCode();
    }

    @Override
    public String toString() {
        return CONST.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const) {
            Const value = (Const) obj;
            return Objects.equals(value.CONST, CONST);
        }
        return false;
    }

    @Override
    public int getPriority() {
        return 500;
    }
}
