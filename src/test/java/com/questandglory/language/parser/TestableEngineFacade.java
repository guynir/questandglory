package com.questandglory.language.parser;

import com.questandglory.engine.*;
import com.questandglory.engine.channels.Mailbox;
import com.questandglory.engine.messages.ClientMessage;
import com.questandglory.engine.messages.ServerMessage;
import com.questandglory.services.ChatHandler;
import com.questandglory.services.ChatService;
import com.questandglory.services.JsonChatResponseDeserializer;
import com.questandglory.template.FreemarkerStringTemplateEngine;
import com.questandglory.template.StringTemplateEngine;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class TestableEngineFacade implements EngineFacade {

    private final GameState gameState = new InMemoryGameState();
    private final StringTemplateEngine localTemplateEngine = new FreemarkerStringTemplateEngine();
    private final TransientEngineState engineState = new TransientEngineState();

    private ChatService chatService;

    public final List<ServerMessage> postedMessages = new LinkedList<>();

    @Override
    public GameState state() {
        return gameState;
    }

    @Override
    public ExecutionState executionState() {
        return engineState;
    }

    @Override
    public String render(String template) {
        return localTemplateEngine.render(template, gameState.getVariables());
    }

    @Override
    public String translate(String message, Locale targetLanguage) {
        return "";
    }

    @Override
    public void sendMessage(ServerMessage message) {
        postedMessages.add(message);
    }

    @Override
    public Mailbox createMailbox() {
        return null;
    }

    @Override
    public void postMessage(String mailboxId, ClientMessage message) {

    }

    @Override
    public void closeMailbox(Mailbox mailbox) {
    }

    @Override
    public <T> ChatHandler<T> creatChat(Class<T> type) {
        return getChatService().createChatMemory(new JsonChatResponseDeserializer<>(type));
    }

    @Override
    public ChatHandler<String> createSimpleChat() {
        return getChatService().createSimpleChatMemory();
    }

    protected ChatService getChatService() {
        if (chatService == null) {
            throw new IllegalStateException("Chat service is not set.");
        }
        return chatService;

    }

    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }
}
