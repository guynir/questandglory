package com.questandglory.engine.statements;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.bool.BooleanExpression;
import com.questandglory.parser.antlr.Location;

public class WhileConditionStatement extends Statement {

    private final BooleanExpression condition;

    private final Statement statement;

    public WhileConditionStatement(Location location, BooleanExpression condition, Statement statement) {
        super(location);

        this.condition = condition;
        this.statement = statement;
    }

    @Override
    protected void handleInternal(EngineFacade facade) throws InterruptedException {
        while (condition.evaluate(facade)) {
            statement.handleInternal(facade);
        }
    }
}
