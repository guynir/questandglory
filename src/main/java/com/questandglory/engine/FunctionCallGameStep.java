package com.questandglory.engine;

import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FunctionCallGameStep extends GameStep {

    @Getter
    private final String functionName;

    @Getter
    private final String[] arguments;

    @Getter
    private final String targetVariable;

    public FunctionCallGameStep(int lineNumber, String functionName, String[] arguments, String targetVariable) {
        super(lineNumber, GameStepEnum.FUNCTION_CALL);
        this.functionName = functionName;
        this.arguments = arguments;
        this.targetVariable = targetVariable;
    }

    /**
     * Validate that the target variable is well-defined and function name is valid.
     *
     * @param steps List of all steps in the game.
     */
    @Override
    public void validate(List<GameStep> steps) {
        validateVariableDefinition(steps);
        validateFunctionName();
    }

    @Override
    public GameStep execute(EngineExecutionContext context) {
        return context.getNextStep();
    }

    protected void validateVariableDefinition(List<GameStep> steps) {
        Optional<DefineVariableGameStep> variableDefinition = findFirst(steps,
                DefineVariableGameStep.class,
                step -> step.getVariableName().equals(this.targetVariable));

        if (variableDefinition.isPresent()) {
            // If variable is defined, make sure it is of the right type (must be STRING for now) and must be declared
            // before this step.
            DefineVariableGameStep defineVariableGameStep = variableDefinition.get();
            if (defineVariableGameStep.getLineNumber() > this.getLineNumber()) {
                throw new IllegalStateException("Variable "
                        + defineVariableGameStep.getVariableName()
                        + " must be declared before line #"
                        + getLineNumber()
                        + ".");
            }

            if (!VariableTypeEnum.STRING.equals(defineVariableGameStep.getVariableType())) {
                throw new IllegalStateException("Variable "
                        + defineVariableGameStep.getVariableName()
                        + " must be declared as STRING variable type."
                );
            }
        } else {
            throw new IllegalStateException("Target variable '" + this.targetVariable + "' is not defined.");
        }
    }

    protected void validateFunctionName() {
        Set<String> functionNames = Set.of("read", "chat");
        if (!functionNames.contains(functionName)) {
            throw new IllegalStateException("Unknown function name: '" + functionName + "'.");
        }
    }
}
