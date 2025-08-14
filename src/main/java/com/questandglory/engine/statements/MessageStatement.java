package com.questandglory.engine.statements;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.string.StringExpression;
import com.questandglory.engine.messages.server.ShowTextMessageMessage;
import com.questandglory.parser.antlr.Location;
import com.questandglory.utils.GlobalIdGenerator;
import lombok.Getter;

public class MessageStatement extends Statement {

    @Getter
    private final StringExpression expression;

    public MessageStatement(Location location, StringExpression expression) {
        super(location);
        this.expression = expression;
    }

    @Override
    protected void handleInternal(EngineFacade facade) {
        String renderedMessage = resolveMessage(facade);
        facade.sendMessage(new ShowTextMessageMessage(GlobalIdGenerator.generateId(), renderedMessage));
    }

    protected String resolveMessage(EngineFacade facade) {
        return expression.evaluate(facade);
    }
}
