package com.calculator;

import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

class UnknownCharException extends Exception {
    public UnknownCharException(char c) {
        super(String.format("Unknown character `%c`", c));
    }
}

class Lexer {

    private int index = 0;
    private String source;

    public Lexer(String source) {
        this.source = source;
    }

    public List<Token> tokenize() throws UnknownCharException {
        List<Token> tokens = new ArrayList<>();

        while (index < source.length()) {
            char c = source.charAt(index);
            if (Character.isWhitespace(c)) {
                index += 1;
                continue;
            }
            if (c == '+') {
                index += 1;
                tokens.add(new Token(TokenType.PLUS, String.valueOf(c)));
            } else if (c == '-') {
                index += 1;
                tokens.add(new Token(TokenType.MINUS, String.valueOf(c)));
            } else if (c == '*') {
                index += 1;
                tokens.add(new Token(TokenType.MULT, String.valueOf(c)));
            } else if (c == '/') {
                index += 1;
                tokens.add(new Token(TokenType.DIV, String.valueOf(c)));
            } else if (c == '(') {
                index += 1;
                tokens.add(new Token(TokenType.OPEN_PAREN, String.valueOf(c)));
            } else if (c == ')') {
                index += 1;
                tokens.add(new Token(TokenType.CLOSE_PAREN, String.valueOf(c)));
            } else if (Character.isDigit(c)) {
                tokens.add(parseNumber());
            } else {
                throw new UnknownCharException(c);
            }
        }

        return tokens;
    }

    private Token parseNumber() {
        int start = index;
        while (index < source.length() && Character.isDigit(source.charAt(index))) {
            index += 1;
        }
        return new Token(TokenType.NUMBER, source.substring(start, index));
    }
}
