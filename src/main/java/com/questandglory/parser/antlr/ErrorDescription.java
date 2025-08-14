package com.questandglory.parser.antlr;

public record ErrorDescription(Location location,
                               String scriptLine,
                               String errorMessage) {
}
