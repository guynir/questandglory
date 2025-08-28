package com.questandglory.engine;

import com.questandglory.engine.channels.Channels;
import com.questandglory.engine.statements.ProgramStatement;
import com.questandglory.services.ChatService;
import com.questandglory.services.Language;
import com.questandglory.services.Languages;
import lombok.Getter;
import lombok.Setter;

public class GameEngine {

    private final ProgramStatement program;

    @Getter
    private final ChatService chatService;

    @Getter
    private final Channels channels;

    private final Thread executionThread = new Thread(this::run);

    @Getter
    private final String gamePlayId;

    private GameEngineStateEnum state = GameEngineStateEnum.READY;

    /**
     * Defines the original language of the game, which can be different from the current language if the player has
     * changed it.
     */
    @Getter
    private final Language originalLanguage = Languages.DEFAULT_LANGUAGE;

    /**
     * Defines the current language of the game, which can be changed by the player during the game.
     */
    @Getter
    @Setter
    private Language language = Languages.DEFAULT_LANGUAGE;

    /**
     * Class constructor.
     */
    public GameEngine(ProgramStatement program, Channels channels, String gamePlayId, ChatService chatService) {
        this.program = program;
        this.channels = channels;
        this.gamePlayId = gamePlayId;
        this.chatService = chatService;
    }

    /**
     * Execute the game. Blocks until game is over.
     *
     * @throws InterruptedException If the execution is interrupted.
     */
    public void execute() throws InterruptedException {
        if (state != GameEngineStateEnum.STARTING && state != GameEngineStateEnum.READY) {
            throw new IllegalStateException("Game is not in a ready/starting state.");
        }
        state = GameEngineStateEnum.RUNNING;

        EngineExecutionFacadeImpl facade = new EngineExecutionFacadeImpl(this);
        program.handle(facade);
    }

    /**
     * Runs the game in a dedicated thread.
     * Catches InterruptedException to allow graceful shutdown.
     */
    protected void run() {
        try {
            execute();
        } catch (InterruptedException ex) {
            // Handle interruption gracefully.
        }
    }

    /**
     * Starts the game in a dedicated thread.
     */
    public void startGame() {
        switch (state) {
            case READY -> {
                state = GameEngineStateEnum.STARTING;
                executionThread.start();
            }
            case STARTING -> throw new IllegalStateException("Game is currently starting..");
            case RUNNING -> throw new IllegalStateException("Game is already running.");
            case SHUTTING_DOWN, STOPPED -> throw new IllegalStateException("Game has already finished.");
        }
    }


}
