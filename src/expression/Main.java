package expression;

import expression.exceptions.*;

public class Main {
    public static void main(String[] args) {
        try {
            TripleExpression expression = new ExpressionParser().parse("reverse(2147483647)");
            try {
                int result = expression.evaluate(0, 0 ,0);
                System.out.printf("%s\n", result);
            } catch (DivideByZeroException e) {
                System.out.printf("%s\n", "division by zero");
            } catch (OverflowExpressionException e) {
                System.out.printf("%s\n", "overflow");
            }
        } catch (ParserException e) {
            System.out.println("Parsing error: " + e.getMessage());
        }
//        try {
//            TripleExpression expression = new ExpressionParser().parse("1000000*x*x*x*x*x/(x-1)");
//            System.out.println("x\t\tf");
//            for (int i = 0; i < 11; i++) {
//                try {
//                    int result = expression.evaluate(i, 0 ,0);
//                    System.out.printf("%s\t\t%s\n", i, result);
//                } catch (DivideByZeroException e) {
//                    System.out.printf("%s\t\t%s\n", i, "division by zero");
//                } catch (OverflowExpressionException e) {
//                    System.out.printf("%s\t\t%s\n", i, "overflow");
//                }
//            }
//        } catch (ParserException e) {
//            System.out.println("Parsing error: " + e.getMessage());
//        }
    }
}
