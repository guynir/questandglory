package com.questandglory.services;

import com.questandglory.parser.antlr.ParsingResults;

public interface CompilingService {

    ParsingResults compile(String script) throws CompilingException;

}
