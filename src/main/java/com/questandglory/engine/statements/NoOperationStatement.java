package com.questandglory.engine.statements;

import com.questandglory.engine.EngineFacade;
import com.questandglory.parser.antlr.Location;

public class NoOperationStatement extends Statement {

    public NoOperationStatement(Location location) {
        super(location);
    }

    @Override
    protected void handleInternal(EngineFacade facade) throws InterruptedException {

    }
}
