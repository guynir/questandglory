package com.questandglory.services;

public interface ChatService {

    ChatHandler<String> createSimpleChatMemory();

    <T> ChatHandler<T> createChatMemory(ChatResponseDeserializer<T> deserializer);

    MessageTranslator createMessageTranslator();

    ScriptParser createScriptParser();
}
