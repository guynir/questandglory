package com.questandglory.services;

import com.questandglory.engine.GameEngine;
import com.questandglory.engine.channels.ChannelFactory;
import com.questandglory.language.compiler.CompiledScript;
import com.questandglory.language.compiler.Compiler;
import com.questandglory.language.compiler.DefaultCompiler;
import com.questandglory.utils.GlobalIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class GameEngineServiceImpl implements GameEngineService {

    private final ChannelFactory channelFactory;

    private final Map<String, GameEngine> enginesMap = new HashMap<>();

    private final ChatService chatService;

    private final Logger logger = LoggerFactory.getLogger(GameEngineServiceImpl.class);

    public GameEngineServiceImpl(ChannelFactory channelFactory, ChatService chatService) {
        this.channelFactory = channelFactory;
        this.chatService = chatService;
    }

    public GameEngine createGameEngine() {
        String script;
        Resource scriptResource = new ClassPathResource("games/GuessWhoIsTheFamousPerson.cacao");
        try (InputStream in = scriptResource.getInputStream()) {
            byte[] data = in.readAllBytes();
            script = new String(data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Compiler compiler = new DefaultCompiler();
        CompiledScript results = compiler.compile(script);

        String gamePlayId = GlobalIdGenerator.generateId();
        GameEngine engine = new GameEngine(results.program(),
                channelFactory.createChannels(gamePlayId),
                gamePlayId,
                chatService);
        enginesMap.put(gamePlayId, engine);
        return engine;
    }

    @Override
    public GameEngine findGameEngine(String gamePlayId) {
        Assert.hasText(gamePlayId, "Game play ID cannot be null or empty.");

        GameEngine engine = enginesMap.get(gamePlayId);
        if (engine == null) {
            throw new UnknownGamePlayException("No game engine found for ID: " + gamePlayId);
        }

        return engine;
    }

    @Override
    public void setLanguage(String gamePlayId, Language language) {
        GameEngine engine = findGameEngine(gamePlayId);
        engine.setLanguage(language);
        logger.info("Language for game play {} set to {}.", gamePlayId, language.displayNameEnglish());
    }
}
