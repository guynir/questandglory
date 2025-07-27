package com.questandglory.engine;

import com.questandglory.engine.messages.server.ShowTextMessageMessage;
import lombok.Getter;

import java.io.Serializable;

public class MessageGameStep extends GameStep implements Serializable {

    @Getter
    private final String message;


    public MessageGameStep(int lineNumber, String message) {
        super(lineNumber, GameStepEnum.MESSAGE);
        this.message = message;
        defineComponentKey();
    }

    @Override
    public GameStep execute(EngineExecutionContext context) {
        String renderedMessage = context.render(this.message);
        context.getChannels().getOutgoingMessagesChannel().sendMessage(new ShowTextMessageMessage(getComponentKey(), renderedMessage));
        return context.getNextStep();
    }

}
