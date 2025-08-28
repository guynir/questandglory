package com.questandglory.web.controllers;

import com.questandglory.ResourceNotFoundException;
import com.questandglory.engine.GameEngine;
import com.questandglory.services.GameEngineService;
import com.questandglory.services.Language;
import com.questandglory.services.Languages;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GamePlayController {

    private final GameEngineService service;

    public GamePlayController(GameEngineService service) {
        this.service = service;
    }

    @GetMapping("/api/v1/languages")
    public List<Language> supportedLanguages() {
        return Languages.LANGUAGES;
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

    @CrossOrigin(origins = "*")
    @PostMapping("/api/v1/gameplay/{gamePlayId}/language")
    public void setLanguage(@PathVariable("gamePlayId") String gamePlayId, @RequestBody SetGamePlayLanguageRequest request) {
        Language language = Languages.getByCode(request.isoCode());
        if (language == null) {
            throw new ResourceNotFoundException("Unknown/unsupported language ISO code: " + request.isoCode());
        }

        service.setLanguage(gamePlayId, language);
    }
}
