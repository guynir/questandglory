package com.questandglory.engine;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

public class DefineVariableGameStep extends GameStep implements Serializable {

    @Getter
    private final String variableName;

    @Getter
    private final VariableTypeEnum variableType;

    public DefineVariableGameStep(int lineNumber, String variableName, VariableTypeEnum variableType) {
        super(lineNumber, GameStepEnum.DEFINE_VARIABLE);
        this.variableName = variableName;
        this.variableType = variableType;
    }

    /**
     * Validate that a given variable is defined only once.
     *
     * @param steps List of all steps in the game.
     */
    @Override
    public void validate(List<GameStep> steps) {
        boolean exist = exist(steps,
                DefineVariableGameStep.class,
                step -> step.getVariableName().equals(this.variableName));

        if (exist) {
            throw new IllegalStateException("Variable '" + variableName + "' is defined more than once.");
        }
    }

    @Override
    public GameStep execute(EngineExecutionContext context) {
        return context.getNextStep();
    }
}
