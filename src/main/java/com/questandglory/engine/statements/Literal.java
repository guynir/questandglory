package com.questandglory.engine.statements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Literal<T> {

    protected T value;

    public abstract String asString();

}
