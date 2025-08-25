package com.questandglory.engine;

import com.questandglory.services.ChatHandler;

import java.util.HashMap;
import java.util.Map;

public class ChatObjectsManager {

    public static final String CHAT_HANDLERS = "CHAT_HANDLERS";
    public static final String SYSTEM_MESSAGE = "SYSTEM_MESSAGE";
    private final EngineFacade facade;

    public ChatObjectsManager(EngineFacade facade) {
        this.facade = facade;
    }

    public ChatHandler<String> getChatHandler(String chatId) {
        ChatHandler<String> handler;
        if (chatId == null) {
            handler = facade.createSimpleChat();
            String systemMessage = getGlobalSystemMessage();
            if (systemMessage != null) {
                handler.setSystemMessage(systemMessage);
            }
        } else {
            Map<String, ChatHandler<String>> handlersMap = getChatHandlers();
            handler = handlersMap.get(chatId);
            if (handler == null) {
                handler = facade.createSimpleChat();
                handlersMap.put(chatId, handler);
            }
        }
        return handler;
    }

    public void setSystemMessage(String chatId, String message) {
        if (chatId == null) {
            facade.executionState().set(SYSTEM_MESSAGE, message);
        } else {
            ChatHandler<String> handler = getChatHandler(chatId);
            handler.setSystemMessage(message);
        }
    }

    public String getGlobalSystemMessage() {
        return facade.executionState().get(SYSTEM_MESSAGE);
    }

    private Map<String, ChatHandler<String>> getChatHandlers() {
        Map<String, ChatHandler<String>> map = facade.executionState().get(CHAT_HANDLERS);
        if (map == null) {
            map = new HashMap<>();
            facade.executionState().set(CHAT_HANDLERS, map);
        }
        return map;
    }


}
