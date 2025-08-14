package com.questandglory.engine;

import com.questandglory.engine.channels.Channels;
import com.questandglory.engine.channels.Mailbox;
import com.questandglory.engine.messages.ClientMessage;
import com.questandglory.engine.messages.ServerMessage;
import com.questandglory.services.ChatHandler;
import com.questandglory.services.ChatService;
import com.questandglory.services.JsonChatResponseDeserializer;
import com.questandglory.services.MessageTranslator;
import com.questandglory.template.FreemarkerStringTemplateEngine;
import com.questandglory.template.StringTemplateEngine;

import java.util.Locale;

public class EngineExecutionFacadeImpl implements EngineFacade {

    private final Channels channels;
    private final GameState gameState = new InMemoryGameState();
    private final ChatService chatService;
    private final StringTemplateEngine localTemplateEngine = new FreemarkerStringTemplateEngine();
    private final MessageTranslator messageTranslator;
    private final TransientEngineState engineState = new TransientEngineState();

    public EngineExecutionFacadeImpl(Channels channels, ChatService chatService) {
        this.channels = channels;
        this.chatService = chatService;
        this.messageTranslator = chatService.createMessageTranslator();
    }

    @Override
    public GameState state() {
        return gameState;
    }

    @Override
    public ExecutionState executionState() {
        return engineState;
    }

    public String render(String template) {
        return localTemplateEngine.render(template, gameState.getVariables());
    }

    public String translate(String message, Locale targetLanguage) {
        return messageTranslator.translate(message, targetLanguage.getDisplayLanguage());
    }

    @Override
    public void sendMessage(ServerMessage message) {
        channels.send(message);
    }

    @Override
    public Mailbox createMailbox() {
        return channels.incomingMessagesChannel.createMailbox();
    }

    @Override
    public void postMessage(String mailboxId, ClientMessage message) {
        channels.incomingMessagesChannel.postMessage(mailboxId, message);
    }

    @Override
    public void closeMailbox(Mailbox mailbox) {
        channels.incomingMessagesChannel.closeMailbox(mailbox);
    }

    public <T> ChatHandler<T> creatChat(Class<T> type) {
        return chatService.createChatMemory(new JsonChatResponseDeserializer<>(type));
    }

    @Override
    public ChatHandler<String> createSimpleChat() {
        return chatService.createSimpleChatMemory();
    }

}
