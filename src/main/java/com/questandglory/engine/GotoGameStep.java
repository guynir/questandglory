package com.questandglory.engine;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

public class GotoGameStep extends GameStep implements Serializable {

    @Getter
    private final String targetLabel;

    public GotoGameStep(int lineNumber, String targetLabel) {
        super(lineNumber, GameStepEnum.GOTO);
        this.targetLabel = targetLabel;
    }

    /**
     * Make sure there's a definition of a target label that matches this step.
     *
     * @param steps List of all steps in the game.
     */
    @Override
    public void validate(List<GameStep> steps) {
        boolean exist = exist(steps,
                LabelGameStep.class,
                step -> step.getLabel().equals(this.targetLabel));

        if (exist) {
            // Reaching this point indicates that there's no matching label to go to.
            throw new IllegalStateException("No label definition found for '" + targetLabel + "'.");
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public GameStep execute(EngineExecutionContext context) {
        return findFirst(context.getSteps(), LabelGameStep.class, step -> step.getLabel().equals(this.targetLabel)).get();
    }
}
