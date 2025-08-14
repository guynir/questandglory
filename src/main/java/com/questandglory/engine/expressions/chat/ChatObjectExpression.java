package com.questandglory.engine.expressions.chat;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.AbstractExpression;
import com.questandglory.services.ChatHandler;

import java.util.Map;

public class ChatObjectExpression extends AbstractExpression<ChatHandler> {

    public ChatObjectExpression() {
        super(ChatHandler.class);
    }

    @Override
    public ChatHandler<Map> evaluate(EngineFacade facade) {
        return facade.creatChat(Map.class);
    }

    @Override
    public Class<ChatHandler> getType() {
        return ChatHandler.class;
    }
}
