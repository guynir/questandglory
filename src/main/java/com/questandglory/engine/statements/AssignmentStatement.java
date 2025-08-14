package com.questandglory.engine.statements;

import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.expressions.Expression;
import com.questandglory.parser.antlr.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentStatement extends Statement {

    private final String variableName;
    private final Expression<?> expression;

    public AssignmentStatement(Location location, String variableName, Expression<?> expression) {
        super(location);
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    protected void handleInternal(EngineFacade facade) throws InterruptedException {
        facade.state().setVariable(variableName, expression.evaluate(facade));
    }
}
