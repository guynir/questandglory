package com.questandglory.language.parser;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.GameState;
import com.questandglory.engine.InMemoryGameState;
import com.questandglory.engine.statements.*;
import com.questandglory.language.compiler.Compiler;
import com.questandglory.language.compiler.DefaultCompiler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestAntlrScriptParserImpl {

    private final Compiler compiler = new DefaultCompiler();

    /**
     * Test that parser successfully parses a simple script with a message statement. The message is a composite
     * string expression (made of literal strings, variables and numbers).
     */
    @Test
    @DisplayName("Test should parse a simple script with a message statement")
    public void testShouldGenerateMessage() {
        String script = """
                MESSAGE ("Hello, Mr. " + name + "! Your number is " + 12);
                """;

        GameState state = new InMemoryGameState();
        state.registerVariable("name", String.class);

        ProgramStatement program = compiler.compile(script, state).program();
        Statements statements = program.getStatements();
        EngineFacade facade = new TestableEngineFacade();
        facade.state().registerVariable("name", String.class, "John");

        assertThat(statements.size()).isEqualTo(1);
        assertThat(statements.getStatements().getFirst()).isInstanceOf(MessageStatement.class);
        MessageStatement statement = (MessageStatement) statements.getStatements().getFirst();
        String message = facade.render(statement.getExpression().evaluate(facade));
        assertThat(message).isEqualTo("Hello, Mr. John! Your number is 12");
    }

    /**
     * Test that parser successfully parses a script with multiple statements, including a message and an input
     * statement.
     */
    @Test
    @DisplayName("Test should parse a script with a message and input statement")
    public void testShouldGenerateMessageAndInputStatements() {
        String script = """
                MESSAGE ("What is your name?");
                name = INPUT();
                """;

        ProgramStatement program = compiler.compile(script).program();

        // Make sure the program has been parsed correctly two statements -- Message and Input.
        Statements statements = program.getStatements();
        assertThat(statements.size()).isEqualTo(2);
        assertThat(statements.getStatements().getFirst()).isInstanceOf(MessageStatement.class);
        assertThat(statements.getStatements().getLast()).isInstanceOf(InputStatement.class);

        // Assert that the target variable for the input statement is set correctly.
        InputStatement inputStatement = (InputStatement) statements.getStatements().getLast();
        assertThat(inputStatement.getVariableName()).isEqualTo("name");
    }


}
