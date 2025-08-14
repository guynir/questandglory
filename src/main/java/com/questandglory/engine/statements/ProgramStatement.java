package com.questandglory.engine.statements;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.messages.server.ShowTextMessageMessage;
import com.questandglory.parser.antlr.Location;
import com.questandglory.utils.GlobalIdGenerator;

public class ProgramStatement extends Statement implements StatementsContainer {

    private Statements statements;

    public ProgramStatement(Location location) {
        super(location);
    }

    @Override
    public Statements getStatements() {
        return statements;
    }

    @Override
    public void setStatements(Statements statements) {
        this.statements = statements;
    }

    @Override
    protected void handleInternal(EngineFacade facade) throws InterruptedException {
        for (Statement statement : statements) {
            statement.handleInternal(facade);
        }
        facade.sendMessage(new ShowTextMessageMessage(GlobalIdGenerator.generateId(), "The end !"));
    }

}
