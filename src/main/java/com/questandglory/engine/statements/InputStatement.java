package com.questandglory.engine.statements;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.channels.Mailbox;
import com.questandglory.engine.messages.client.TextInputMessage;
import com.questandglory.engine.messages.server.RemoveComponentMessage;
import com.questandglory.engine.messages.server.RequestUserTextInputMessage;
import com.questandglory.engine.messages.server.ShowTextMessageMessage;
import com.questandglory.parser.antlr.Location;
import com.questandglory.utils.GlobalIdGenerator;
import lombok.Getter;

public class InputStatement extends Statement {

    @Getter
    private final String variableName;

    private final String componentKey = "input_" + GlobalIdGenerator.generateId();

    public InputStatement(Location location, String variableName) {
        super(location);
        this.variableName = variableName;
    }

    @Override
    protected void handleInternal(EngineFacade facade) throws InterruptedException {
        try (Mailbox mailbox = facade.createMailbox()) {
            logger.info("Creating mailbox for user input with ID: {}", mailbox.getMailboxId());
            RequestUserTextInputMessage message = new RequestUserTextInputMessage(componentKey, mailbox.getMailboxId());
            facade.sendMessage(message);

            // Block until the user provides input.
            TextInputMessage inputMessage = mailbox.pullMessage(TextInputMessage.class);

            // Store in the context variable for it be accessible in the next steps.
            facade.state().getVariables().put(this.variableName, inputMessage.getText());
            facade.sendMessage(new RemoveComponentMessage(componentKey));
            facade.sendMessage(new ShowTextMessageMessage(componentKey, "> " + inputMessage.getText()));
        }

    }
}
