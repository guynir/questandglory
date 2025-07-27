package com.questandglory.engine;

import com.questandglory.engine.channels.Mailbox;
import com.questandglory.engine.messages.client.TextInputMessage;
import com.questandglory.engine.messages.server.RemoveComponentMessage;
import com.questandglory.engine.messages.server.RequestUserTextInputMessage;
import com.questandglory.engine.messages.server.ShowTextMessageMessage;
import lombok.Getter;

public class InputGameStep extends GameStep {

    @Getter
    protected final String targetVariable;

    public InputGameStep(int lineNumber, String targetVariable) {
        super(lineNumber, GameStepEnum.INPUT);
        this.targetVariable = targetVariable;
        defineComponentKey();
    }

    @Override
    public GameStep execute(EngineExecutionContext context) throws InterruptedException {
        try (Mailbox mailbox = context.getChannels().incomingMessagesChannel.createMailbox()) {
            logger.info("Creating mailbox for user input with ID: {}", mailbox.getMailboxId());
            RequestUserTextInputMessage message = new RequestUserTextInputMessage(getComponentKey(), mailbox.getMailboxId());
            context.getChannels().outgoingMessagesChannel.sendMessage(message);

            // Block until the user provides input.
            TextInputMessage inputMessage = mailbox.pullMessage(TextInputMessage.class);

            // Store in the context variable for it be accessible in the next steps.
            context.getVariables().put(this.targetVariable, inputMessage.getText());
            context.getChannels().getOutgoingMessagesChannel().sendMessage(new RemoveComponentMessage(this.getComponentKey()));
            context.getChannels().getOutgoingMessagesChannel().sendMessage(new ShowTextMessageMessage(getComponentKey(), "> " + inputMessage.getText()));
        }

        return context.getNextStep();
    }
}
