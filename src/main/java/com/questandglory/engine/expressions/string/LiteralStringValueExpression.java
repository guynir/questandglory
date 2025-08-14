package com.questandglory.engine.expressions.string;

import com.questandglory.engine.EngineFacade;
import lombok.Getter;

public class LiteralStringValueExpression extends StringExpression {

    @Getter
    private final String value;

    public LiteralStringValueExpression(String value) {
        this.value = value;
    }

    @Override
    public String evaluate(EngineFacade facade) {
        return facade.render(value);
    }
}
