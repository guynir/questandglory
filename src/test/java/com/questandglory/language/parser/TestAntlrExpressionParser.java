package com.questandglory.language.parser;

import com.questandglory.engine.GameState;
import com.questandglory.engine.statements.AssignmentStatement;
import com.questandglory.parser.LanguageFactory;
import com.questandglory.parser.antlr.AntlrLanguageFactory;
import com.questandglory.parser.antlr.LanguageLexer;
import com.questandglory.parser.antlr.LanguageParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestAntlrExpressionParser {

    private LanguageFactory factory;

    private TestableEngineFacade facade;

    private GameState gameState;

    @BeforeEach
    public void setUp() {
        facade = new TestableEngineFacade();
        gameState = facade.state();
        factory = new AntlrLanguageFactory(gameState);
    }

    @Test
    public void testShouldParseIntegerExpressions() {
        String script = """
                A = 5 + 3 + B;
                """;

        gameState.registerVariable("A", Integer.class);
        gameState.registerVariable("B", Integer.class);

        ParseTree tree = createParser(script, gameState);
        AssignmentStatement statement = factory.assignmentStatement(tree);

        System.out.println(statement);
    }

    @Test
    public void testShouldParseStringExpressions() throws InterruptedException {
        String script = """
                A = B + "world ! -- " + (2 + 3);
                """;

        gameState.registerVariable("A", String.class);
        gameState.registerVariable("B", String.class);
        gameState.setVariable("B", "Hello, ");

        ParseTree tree = createParser(script, gameState);
        AssignmentStatement statement = factory.assignmentStatement(tree);

        statement.handle(facade);
        String result = gameState.getVariable("A");

        assertThat(result).isEqualTo("Hello, world ! -- 5");
    }

    @Test
    public void testShouldParseBooleanExpressions() throws InterruptedException {
        String script = """
                A = (true == (1 < 2)) AND ("hi" == "hi") AND ("NO" != "no") AND (FALSE == false) AND NOT false;
                """;

        gameState.registerVariable("A", Boolean.class);

        ParseTree tree = createParser(script, gameState);
        AssignmentStatement statement = factory.assignmentStatement(tree);

        statement.handle(facade);
        Boolean result = gameState.getVariable("A");
        assertThat(result).isTrue();
    }

    private ParseTree createParser(String script, GameState gameState) {
        LanguageLexer lexer = new LanguageLexer(CharStreams.fromString(script));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LanguageParser parser = new LanguageParser(tokens);
        parser.setGameState(gameState);
        return parser.program();
    }

}
