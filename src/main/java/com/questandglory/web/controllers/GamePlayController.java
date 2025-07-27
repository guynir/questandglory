package com.questandglory.web.controllers;

import com.questandglory.engine.GameEngine;
import com.questandglory.services.GameEngineService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GamePlayController {

    private final GameEngineService service;

    public GamePlayController(GameEngineService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/api/v1/gameplay/")
    public GamePlayCreateResponse createGamePlay() {
        GameEngine engine = service.createGameEngine();
        return new GamePlayCreateResponse(
                engine.getGamePlayId(),
                engine.getChannels().getOutgoingMessagesChannel().getClientQueueId());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/api/v1/gameplay/{gamePlayId}/start")
    public void startGamePlay(@PathVariable("gamePlayId") String gamePlayId) {
        service.findGameEngine(gamePlayId).startGame();
    }
}
