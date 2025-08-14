package com.questandglory.parser.antlr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public record Location(int lineNumber, int columnNumber) implements Comparable<Location> {

    public static Location parse(ParserRuleContext ctx) {
        return parse(ctx.getStart());
    }

    public static Location parse(Token token) {
        return new Location(token.getLine(), token.getCharPositionInLine());
    }

    public static Location of(int lineNumber, int columnNumber) {
        return new Location(lineNumber, columnNumber);
    }

    @Override
    public int compareTo(Location other) {
        if (this.lineNumber < other.lineNumber) {
            return -1;
        } else if (this.lineNumber > other.lineNumber) {
            return 1;
        } else {
            return Integer.compare(this.columnNumber, other.columnNumber);
        }
    }
}
