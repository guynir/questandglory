package com.questandglory.engine;

import java.util.Locale;

public class TranslationGameStep extends GameStep {

    private final Locale targetLanguage;
    private final String message;
    private final String targetVariable;

    public TranslationGameStep(int lineNumber, Locale targetLanguage, String message, String targetVariable) {
        super(lineNumber, GameStepEnum.TRANSLATE);
        this.targetLanguage = targetLanguage;
        this.message = message;
        this.targetVariable = targetVariable;
    }

    @Override
    public GameStep execute(EngineExecutionContext context) throws InterruptedException {
        String renderedMessage = context.render(message);
        String translatedMessage = context.translate(renderedMessage, targetLanguage);
        context.setVariable(targetVariable, translatedMessage);
        return context.getNextStep();
    }
}
