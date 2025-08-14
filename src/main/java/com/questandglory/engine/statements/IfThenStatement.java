package com.questandglory.engine.statements;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.bool.BooleanExpression;
import com.questandglory.parser.antlr.Location;

public class IfThenStatement extends Statement {

    private final BooleanExpression condition;
    private final Statement statement;
    private final Statement elseStatement;

    public IfThenStatement(Location location,
                           BooleanExpression condition,
                           Statement statement,
                           Statement elseStatement) {
        super(location);
        this.condition = condition;
        this.statement = statement;
        this.elseStatement = elseStatement;
    }

    @Override
    protected void handleInternal(EngineFacade facade) throws InterruptedException {
        if (condition.evaluate(facade)) {
            if (statement != null) {
                statement.handleInternal(facade);
            }
        } else {
            if (elseStatement != null) {
                elseStatement.handleInternal(facade);
            }
        }
    }
}
