package com.calculator;

import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("ERROR: no expression provided");
            System.exit(1);
        }

        String expression = args[0];

        Lexer lexer = new Lexer(expression);
        try {
            List<Token> tokens = lexer.tokenize();
            Parser parser = new Parser(tokens);
            Expression expr = parser.parse();
            System.out.println(expression);
            System.out.println(">>> " + expr.result());
        } catch (UnknownCharException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch(ArithmeticException e) {
            System.err.println("Error: Divide by zero is not allowed");
            System.exit(1);
        }
    }
}
