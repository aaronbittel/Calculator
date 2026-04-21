package com.calculator;

import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("ERROR: no equation provided");
            System.exit(1);
        }

        String equation = args[0];

        Lexer lexer = new Lexer(equation);
        try {
            List<Token> tokens = lexer.tokenize();
            System.out.println("Tokens:");
            System.out.println(
                tokens.stream().map(Token::toString).collect(Collectors.joining(", ")));

            Parser parser = new Parser(tokens);
            Expression expr = parser.parse();
            System.out.println("\nParsed AST:");
            System.out.println(expr);

            System.out.println("\nEquation:");
            System.out.println(equation);
            System.out.println(">>> " + expr.result());
        } catch (UnknownCharException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
