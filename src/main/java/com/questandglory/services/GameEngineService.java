package com.questandglory.services;

import com.questandglory.engine.GameEngine;

public interface GameEngineService {

    /**
     * Creates a new game engine instance with a desired game.
     *
     * @return A new game engine.
     */
    GameEngine createGameEngine();

    /**
     * Finds a game engine by its game play ID.
     *
     * @param gamePlayId ID of the game play.
     * @return The game engine associated with the given game play ID.
     * @throws UnknownGamePlayException if no game engine is found for the given ID.
     */
    GameEngine findGameEngine(String gamePlayId) throws UnknownGamePlayException;

    /**
     * Sets alternate language for the game play.
     *
     * @param gamePlayId ID of the game play.
     * @param language   Language to set.
     */
    void setLanguage(String gamePlayId, Language language);

}
