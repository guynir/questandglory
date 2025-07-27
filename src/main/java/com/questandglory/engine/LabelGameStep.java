package com.questandglory.engine;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

public class LabelGameStep extends GameStep implements Serializable {

    @Getter
    private final String label;

    public LabelGameStep(int lineNumber, String label) {
        super(lineNumber, GameStepEnum.LABEL);
        this.label = label;
    }

    /**
     * Validate that this label appears only once.
     *
     * @param steps List of all steps in the game.
     */
    @Override
    public void validate(List<GameStep> steps) {
        boolean exist = exist(steps,
                LabelGameStep.class,
                step -> step.getLabel().equals(this.label));

        if (exist) {
            throw new IllegalStateException("Label '" + this.label + "' defined more than once.");
        }
    }

    @Override
    public GameStep execute(EngineExecutionContext context) {
        return context.getNextStep();
    }
}
