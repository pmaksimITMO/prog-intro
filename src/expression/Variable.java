package expression;

import java.util.Objects;

public class Variable implements AllExpression {
    private final String type;

    public Variable(String type) {
        this.type = type;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int res = 0;
        switch (type) {
            case "x" -> res = x;
            case "y" -> res = y;
            case "z" -> res = z;
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            Variable it = (Variable) obj;
            return Objects.equals(it.type, type);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public int getPriority() {
        return 500;
    }
}
