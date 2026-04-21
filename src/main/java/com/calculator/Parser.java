package com.calculator;

import java.util.List;

abstract class Expression {
    abstract int result();
}

class Binary extends Expression {
    private Expression left;
    private Token operator;
    private Expression right;

    public Binary(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public int result() {
        return switch(operator.getTokenType()) {
            case PLUS  -> left.result() + right.result();
            case MINUS -> left.result() - right.result();
            case MULT  -> left.result() * right.result();
            case DIV   -> left.result() / right.result();
            default    -> throw new IllegalStateException("Illegal binary operator");
        };
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", left, operator, right);
    }
}

class Unary extends Expression {
    private Token operator;
    private Expression expr;

    public Unary(Token operator, Expression expr) {
        this.operator = operator;
        this.expr = expr;
    }

    @Override
    public int result() {
        if (operator.getTokenType() == TokenType.MINUS) {
            return -expr.result();
        }
        throw new IllegalStateException("Only `-` (MINUS) is allowed in Unary Expression");
    }

    @Override
    public String toString() {
        if (operator.getTokenType() == TokenType.MINUS) {
            return "-" + expr;
        }
        throw new IllegalStateException("Only `-` (MINUS) is allowed in Unary Expression");
    }
}

class Literal extends Expression {
    private Token token;

    public Literal(Token token) {
        this.token = token;
    }

    public int result() {
        return Integer.valueOf(token.getValue());
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return token.getValue();
    }
}

class Grouping extends Expression {
    private Expression expr;

    public Grouping(Expression expr) {
        this.expr = expr;
    }

    @Override
    public int result() {
        return expr.result();
    }

    @Override
    public String toString() {
        return "[" + expr + "]";
    }
}

class Parser {
    private List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expression parse() {
        return parseTerm();
    }

    private Expression parseTerm() {
        Expression expr = parseFactor();
        while (peek() == TokenType.PLUS || peek() == TokenType.MINUS) {
            Token operator = advance();
            Expression right = parseFactor();
            expr = new Binary(expr, operator, right);
        }
        return expr;
    }

    private Expression parseFactor() {
        Expression expr = parseUnary();
        while (peek() == TokenType.MULT || peek() == TokenType.DIV) {
            Token operator = advance();
            Expression right = parseUnary();
            expr = new Binary(expr, operator, right);
        }
        return expr;
    }

    private Expression parseUnary() {
        if (peek() == TokenType.MINUS) {
            Token operator = advance();
            Expression right = parseUnary();
            return new Unary(operator, right);
        }
        return parsePrimary();
    }

    private Expression parsePrimary() {
        if (peek() == TokenType.OPEN_PAREN) {
            advance();
            Expression expr = parseTerm();
            consume(TokenType.CLOSE_PAREN);
            return new Grouping(expr);
        } else if (peek() == TokenType.NUMBER) {
            return new Literal(advance());
        }

        throw new IllegalStateException("Unexpected Token in Primary");
    }

    private Token consume(TokenType tokenType) {
        Token token = advance();
        if (token.getTokenType() != tokenType) {
            throw new IllegalStateException(String.format("ERROR: expected `%s`, but got `%s`", tokenType, token.getTokenType()));
        }
        return token;
    }

    private Token advance() {
        if (isEof()) return new Token(TokenType.EOF, "EOF");
        Token token = tokens.get(current);
        current += 1;
        return token;
    }

    private TokenType peek() {
        return isEof() ? TokenType.EOF : tokens.get(current).getTokenType();
    }

    private boolean isEof() {
        return current >= tokens.size();
    }
}
