package expression;

public interface AllExpression extends Expression, TripleExpression, DoubleExpression {
    int getPriority();
}
