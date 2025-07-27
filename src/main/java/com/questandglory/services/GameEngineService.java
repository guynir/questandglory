package com.questandglory.services;

import com.questandglory.engine.GameEngine;

public interface GameEngineService {

    GameEngine createGameEngine();

    GameEngine findGameEngine(String gamePlayId);

}
