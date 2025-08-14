package com.questandglory.parser;

public record ScriptError(int lineNumber, String message, String content) {
}
