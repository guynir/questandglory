package com.questandglory.engine;

import com.questandglory.engine.channels.Channels;
import com.questandglory.engine.statements.ProgramStatement;
import com.questandglory.services.ChatService;
import lombok.Getter;

public class GameEngine {

    private final ProgramStatement program;
    private final ChatService chatService;
    @Getter
    private final Channels channels;
    private final Thread executionThread = new Thread(this::run);
    @Getter
    private final String gamePlayId;

    public GameEngine(ProgramStatement program, Channels channels, String gamePlayId, ChatService chatService) {
        this.program = program;
        this.channels = channels;
        this.gamePlayId = gamePlayId;
        this.chatService = chatService;
    }

    public void execute() throws InterruptedException {
        EngineExecutionFacadeImpl facade = new EngineExecutionFacadeImpl(channels, chatService);
        program.handle(facade);
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
