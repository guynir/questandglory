package com.questandglory.engine.statements;

import com.questandglory.engine.ChatObjectsManager;
import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.string.StringExpression;
import com.questandglory.parser.antlr.Location;

public class SetSystemMessageStatement extends Statement {

    private final String chatId;

    private final StringExpression message;

    public SetSystemMessageStatement(Location location, String chatId, StringExpression message) {
        super(location);
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    protected void handleInternal(EngineFacade facade) throws InterruptedException {
        ChatObjectsManager manager = new ChatObjectsManager(facade);
        manager.setSystemMessage(chatId, message.evaluate(facade));
    }
}
