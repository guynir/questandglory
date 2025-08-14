package com.questandglory.parser.antlr;

import com.questandglory.engine.statements.ProgramStatement;
import com.questandglory.parser.CompilationErrors;

public record ParsingResults(ProgramStatement program,
                             CompilationErrors errors) {

}
