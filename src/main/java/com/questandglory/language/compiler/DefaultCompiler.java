package com.questandglory.language.compiler;

import com.questandglory.engine.statements.ProgramStatement;
import com.questandglory.language.script.Script;
import com.questandglory.language.variables.VariablesDefinition;
import com.questandglory.parser.CompilationException;
import com.questandglory.parser.InternalCompilationErrorException;
import com.questandglory.parser.LanguageFactory;
import com.questandglory.parser.antlr.AntlrLanguageFactory;
import com.questandglory.parser.antlr.LanguageLexer;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.listeners.ErrorListener;
import com.questandglory.parser.antlr.listeners.VariableDeclarationLanguageListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * A default implementation of a compiler.
 *
 * @author Guy Raz Nir
 * @since 2025/08/20
 */
public class DefaultCompiler implements Compiler {

    /**
     * Class logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultCompiler.class);

    /**
     * Class constructor.
     */
    public DefaultCompiler() {
    }

    @Override
    public CompiledScript compile(String script) throws CompilationException, InternalCompilationErrorException {
        return compile(Script.from(script), null);
    }

    @Override
    public CompiledScript compile(Script script) throws CompilationException, InternalCompilationErrorException {
        return compile(script, null);
    }

    @Override
    public CompiledScript compile(Script script, VariablesDefinition variables)
            throws CompilationException, InternalCompilationErrorException {
        Assert.notNull(script, "Script cannot be null");
        if (variables == null) {
            variables = new VariablesDefinition();
        }

        LanguageFactory factory = new AntlrLanguageFactory(variables);
        LanguageLexer lexer = new LanguageLexer(CharStreams.fromString(script.getScript()));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LanguageParser parser = new LanguageParser(tokens);
        parser.addErrorListener(new ErrorListener());
        parser.addParseListener(new VariableDeclarationLanguageListener(variables, factory));
        parser.setCompilerHelper(new CompilerHelper(variables));

        try {
            ProgramStatement program = factory.programStatement(parser.program());
            return new CompiledScript(program);
        } catch (CompilationException ex) {
            String spaces = " ".repeat(Math.max(0, ex.getLocation().columnNumber));
            String message = "Compilation error." +
                    System.lineSeparator() +
                    script.getLine(ex.getLocation().lineNumber)
                    + System.lineSeparator()
                    + " ".repeat(Math.max(0, ex.getLocation().columnNumber))
                    + "^--- "
                    + System.lineSeparator()
                    + ex.getMessage();

            logger.error(message, ex);
            throw ex;
        }
    }
}
