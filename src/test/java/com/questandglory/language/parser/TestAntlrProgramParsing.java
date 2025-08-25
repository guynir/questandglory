package com.questandglory.language.parser;

import com.questandglory.language.compiler.CompiledScript;
import com.questandglory.language.compiler.Compiler;
import com.questandglory.language.compiler.DefaultCompiler;
import com.questandglory.parser.CompilationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.questandglory.language.parser.Helpers.execute;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestAntlrProgramParsing {

    private final Compiler compiler = new DefaultCompiler();

    @Test
    @DisplayName("Test should parse empty statements block")
    public void testShouldParseEmptyStatement() {
        String script = "";

        CompiledScript results = compiler.compile(script);

        assertThat(results.program().getStatements()).isNotNull();
        assertThat(results.program().getStatements()).hasSize(0);
    }

    @Test
    @DisplayName("Test should skip comment lines")
    public void testShouldSkipCommentLines() {
        String script = """
                // A comment line.
                % Another comment line.
                """;

        CompiledScript results = compiler.compile(script);

        assertThat(results.program().getStatements()).isNotNull();
        assertThat(results.program().getStatements()).hasSize(0);
    }

    @Test
    @DisplayName("Test should parse if ... then ... else ... statement")
    public void testShouldParseIfThenConditionWithSingleStatement() throws InterruptedException, IOException {
        String script = """
                // Define a local integer variable.
                var A: Integer = 0;
                
                // Assign the active the ... ELSE ... block and assign '2' to variable.
                IF (FALSE) THEN A=1; ELSE A=2; END IF
                """;

        Helpers.ExecutionResult result = execute(script);

        Integer A = result.facade().state().getVariable("A");
        org.assertj.core.api.AssertionsForClassTypes.assertThat(A).isEqualTo(2);
    }

    @Test
    public void testAskWithStringResponse() throws InterruptedException, IOException {
        String script = """
                var fastestCar : String;
                setSystemMessage("You are an expert on cars.");
                fastestCar = ask("What is fastest car ever ? Respond with a single line with only manufacturer name and model.");
                """;

        Helpers.ExecutionResult result = execute(script);

        String fastestCar = result.facade().state().getVariable("fastestCar");
        assertThat(fastestCar).containsIgnoringCase("Bugatti");
        System.out.println("Fastest car: " + fastestCar);
    }

    @Test
    public void testAskWithIntegerResponse() throws InterruptedException, IOException {
        String script = """
                var speedOfLight : Integer;
                speedOfLight = ask("What is the speed of light in KM/s. Provide digits only.");
                """;

        Helpers.ExecutionResult result = execute(script);
        Integer speedOfLight = result.facade().state().getVariable("speedOfLight");
        assertThat(speedOfLight).isGreaterThan(299790).isLessThan(300001);
        System.out.println("Speed of light: " + speedOfLight);
    }

    @Test
    public void testAskWithBooleanResponse() throws InterruptedException, IOException {
        String script = """
                var isDarker: Boolean;
                isDarker = ask("Is black darker than white ? Provide a boolean answer - either Yes or No.");
                """;

        Helpers.ExecutionResult result = execute(script);
        Boolean isDarker = result.facade().state().getVariable("isDarker");
        assertThat(isDarker).isTrue();
    }

    @Test
    public void testShouldRunWhileLoop() throws IOException, InterruptedException {
        String script = """
                // While loop statement.
                var counter : Integer = 0;
                while counter < 4 do
                    counter = counter + 1;
                end while
                """;

        Helpers.ExecutionResult result = execute(script);
        Integer counter = result.facade().state().getVariable("counter");
        assertThat(counter).isEqualTo(4);
    }

    @Test
    public void testShouldFailWithParsingErrors() {
        String script = """
                var counter = 0;
                """;
        assertThatExceptionOfType(CompilationException.class).isThrownBy(() -> Helpers.compile(script));
    }
}

