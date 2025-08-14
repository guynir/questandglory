package com.questandglory.parser.antlr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class Location implements Comparable<Location> {

    /**
     * Line number, starts at 1.
     */
    public final int lineNumber;

    /**
     * Column number (offset within the line). Strats at 1.
     */
    public final int columnNumber;

    /**
     * Text associated with the location. Maybe an empty string.
     */
    public final String text;

    public Location(int lineNumber, int columnNumber, String text) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.text = text;
    }

    public static Location from(ParserRuleContext ctx) {
        return from(ctx.getStart());
    }

    public static Location from(Token token) {
        return new Location(token.getLine(), token.getCharPositionInLine(), token.getText());
    }

    public static Location of(int lineNumber, int columnNumber, String text) {
        return new Location(lineNumber, columnNumber, text);
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
