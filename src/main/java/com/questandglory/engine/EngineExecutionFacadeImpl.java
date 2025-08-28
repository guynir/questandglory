package com.questandglory.engine;

import com.questandglory.engine.channels.Mailbox;
import com.questandglory.engine.messages.ClientMessage;
import com.questandglory.engine.messages.ServerMessage;
import com.questandglory.services.ChatHandler;
import com.questandglory.services.JsonChatResponseDeserializer;
import com.questandglory.services.Language;
import com.questandglory.services.MessageTranslator;
import com.questandglory.template.FreemarkerStringTemplateEngine;
import com.questandglory.template.StringTemplateEngine;

public class EngineExecutionFacadeImpl implements EngineFacade {

    private final GameState gameState = new InMemoryGameState();
    private final StringTemplateEngine localTemplateEngine = new FreemarkerStringTemplateEngine();
    private final MessageTranslator messageTranslator;
    private final TransientEngineState engineState = new TransientEngineState();
    private final GameEngine parent;

    public EngineExecutionFacadeImpl(GameEngine parent) {
        this.messageTranslator = parent.getChatService().createMessageTranslator();
        this.parent = parent;
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

    public String translate(String message, Language targetLanguage) {
        return messageTranslator.translate(message, targetLanguage.displayNameEnglish());
    }

    @Override
    public void sendMessage(ServerMessage message) {
        parent.getChannels().send(message);
    }

    @Override
    public Mailbox createMailbox() {
        return parent.getChannels().incomingMessagesChannel.createMailbox();
    }

    @Override
    public void postMessage(String mailboxId, ClientMessage message) {
        parent.getChannels().incomingMessagesChannel.postMessage(mailboxId, message);
    }

    @Override
    public void closeMailbox(Mailbox mailbox) {
        parent.getChannels().incomingMessagesChannel.removeMailbox(mailbox.getMailboxId());
    }

    public <T> ChatHandler<T> creatChat(Class<T> type) {
        return parent.getChatService().createChatMemory(new JsonChatResponseDeserializer<>(type));
    }

    @Override
    public ChatHandler<String> createSimpleChat() {
        return parent.getChatService().createSimpleChatMemory();
    }

    @Override
    public boolean isTranslationRequired() {
        return !parent.getLanguage().equals(parent.getOriginalLanguage());
    }

    @Override
    public Language getCurrentLanguage() {
        return parent.getLanguage();
    }
}
