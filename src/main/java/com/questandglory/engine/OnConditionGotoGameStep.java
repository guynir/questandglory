package com.questandglory.engine;

import com.questandglory.engine.conditions.Condition;

import java.util.List;

public class OnConditionGotoGameStep extends GameStep {

    private final String targetLabel;
    private final Condition condition;

    public OnConditionGotoGameStep(int lineNumber, Condition condition, String targetLabel) {
        super(lineNumber, GameStepEnum.ON_CONDITION_GOTO);
        this.condition = condition;
        this.targetLabel = targetLabel;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public GameStep execute(EngineExecutionContext context) throws InterruptedException {
        if (condition.evaluate(context)) {
            return findFirst(context.getSteps(),
                    LabelGameStep.class,
                    step -> step.getLabel().equals(this.targetLabel)).get();
        } else {
            return context.getNextStep();
        }
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
}
