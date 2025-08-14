package com.questandglory.parser;

import com.questandglory.parser.antlr.ParsingResults;

public interface ScriptParser {

    ParsingResults parseScript(String script);

}
