package com.questandglory.language.parser;

import com.questandglory.engine.GameState;
import com.questandglory.engine.statements.ProgramStatement;
import com.questandglory.language.compiler.Compiler;
import com.questandglory.language.compiler.DefaultCompiler;
import com.questandglory.language.script.Script;
import com.questandglory.language.variables.VariablesDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestAntlrExpressionParser {

    private TestableEngineFacade facade;

    private GameState gameState;

    private VariablesDefinition variables;

    private final Compiler compiler = new DefaultCompiler();

    @BeforeEach
    public void setUp() {
        facade = new TestableEngineFacade();
        gameState = facade.state();
        variables = new VariablesDefinition();
    }

    @Test
    public void testShouldParseIntegerExpressions() {
        String script = """
                A = 5 + 3 + B;
                """;

        variables.registerVariable("A", Integer.class);
        variables.registerVariable("B", Integer.class);

        ProgramStatement program = compiler.compile(Script.from(script), variables).program();
    }

    @Test
    public void testShouldParseStringExpressions() throws InterruptedException {
        String script = """
                A = B + "world ! -- " + (2 + 3);
                """;

        variables.registerVariable("A", String.class);
        variables.registerVariable("B", String.class);

        gameState.registerVariable("A", String.class);
        gameState.registerVariable("B", String.class);
        gameState.setVariable("B", "Hello, ");

        ProgramStatement program = compiler.compile(Script.from(script), variables).program();
        program.handle(facade);
        String result = gameState.getVariable("A");

        assertThat(result).isEqualTo("Hello, world ! -- 5");
    }

    @Test
    public void testShouldParseBooleanExpressions() throws InterruptedException {
        String script = """
                A = (true == (1 < 2)) AND ("hi" == "hi") AND ("NO" != "no") AND (FALSE == false) AND NOT false;
                """;

        variables.registerVariable("A", Boolean.class);

        ProgramStatement program = compiler.compile(Script.from(script), variables).program();

        gameState.registerVariable("A", Boolean.class);
        program.handle(facade);
        Boolean result = gameState.getVariable("A");
        assertThat(result).isTrue();
    }


}
