package com.calculator;

import java.util.Objects;

enum TokenType {
    NUMBER,
    PLUS,
    MINUS,
    MULT,
    DIV,
    OPEN_PAREN,
    CLOSE_PAREN,
    EOF;

    @Override
    public String toString() {
        return switch(this) {
            case NUMBER      -> "NUMBER";
            case PLUS        -> "+";
            case MINUS       -> "-";
            case MULT        -> "*";
            case DIV         -> "/";
            case OPEN_PAREN  -> "(";
            case CLOSE_PAREN -> ")";
            case EOF         -> "EOF";
        };
    }
}

class Token {
    private TokenType tokenType;
    private String value;

    public Token(TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenType, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token other = (Token)o;
        return tokenType.equals(other.getTokenType()) && value.equals(other.getValue());
    }

    @Override
    public String toString() {
        return switch(tokenType) {
            case NUMBER -> String.format("%s(%s)", tokenType, value);
            default     -> tokenType.toString();
        };
    }
}
