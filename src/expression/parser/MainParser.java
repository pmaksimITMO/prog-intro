package expression.parser;

import expression.TripleExpression;

public class MainParser {
    public static void main(String[] args) {
        ExpressionParser Parser = new ExpressionParser();
        TripleExpression result = Parser.parse("-reverse 1");
        System.out.println(result.toMiniString());
    }
}
