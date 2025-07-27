package com.questandglory.engine;

import com.questandglory.engine.channels.Channels;
import com.questandglory.services.ChatService;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameEngine {

    private final List<GameStep> steps = new ArrayList<>();

    private final Thread executionThread = new Thread(this::run);

    private final ChatService chatService;

    @Getter
    private final Channels channels;

    @Getter
    private final String gamePlayId;

    public GameEngine(List<GameStep> steps, Channels channels, String gamePlayId, ChatService chatService) {
        this.steps.addAll(steps);
        this.channels = channels;
        this.gamePlayId = gamePlayId;
        this.chatService = chatService;
    }

    public void execute() throws InterruptedException {

        GameStep currentStep = steps.getFirst();

        EngineExecutionContext context = new EngineExecutionContext(channels, steps, new HashMap<>(), currentStep, chatService);
        steps.forEach(step -> step.prepare(context));

        while (currentStep != null) {
            int index = steps.indexOf(currentStep);
            GameStep nextStep = (index + 1 < steps.size()) ? steps.get(index + 1) : null;
            context.setNextStep(nextStep);

            currentStep = currentStep.execute(context);
        }
    }

    protected void run() {
        try {
            execute();
        } catch (InterruptedException ex) {
            // Handle interruption gracefully.
        }
    }

    public void startGame() {
        executionThread.start();
    }


}
