package com.questandglory.parser.antlr;

import com.questandglory.engine.GameState;
import com.questandglory.engine.InMemoryGameState;
import com.questandglory.engine.statements.ProgramStatement;
import com.questandglory.parser.CompilationErrors;
import com.questandglory.parser.CompilationException;
import com.questandglory.parser.LanguageFactory;
import com.questandglory.parser.ScriptParser;
import com.questandglory.parser.antlr.listeners.ErrorListener;
import com.questandglory.parser.antlr.listeners.VariableDeclarationLanguageListener;
import org.antlr.v4.runtime.ANTLRErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class AntlrScriptParserImpl implements ScriptParser {

    @Override
    public ParsingResults parseScript(String script) {
        return parseScript(script, new InMemoryGameState());
    }

    public ParsingResults parseScript(String script, GameState gameState) {
        LanguageFactory factory = new com.questandglory.parser.antlr.AntlrLanguageFactory(gameState);
        CompilationErrors errors = new CompilationErrors();

        LanguageLexer lexer = new LanguageLexer(CharStreams.fromString(script));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LanguageParser parser = new LanguageParser(tokens);
        parser.addErrorListener(new ErrorListener());
        ANTLRErrorStrategy strategy = parser.getErrorHandler();

        parser.addParseListener(new VariableDeclarationLanguageListener(gameState, factory));
        parser.setGameState(gameState);
        ProgramStatement program;
        try {
            program = factory.programStatement(parser.program());
            return new ParsingResults(program, null);
        } catch (CompilationException ex) {
            System.out.println(script.split("\n")[ex.getLocation().lineNumber() - 1]);
            String buf = " ".repeat(Math.max(0, ex.getLocation().columnNumber()))
                    + "^ -- "
                    + ex.getMessage();
            System.out.println(buf);
            throw ex;
        }
    }
}
