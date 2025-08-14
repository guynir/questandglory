package com.questandglory.engine.constructs;

import lombok.Data;

@Data
public class Identifier {

    private String name;

    public Identifier(String name) {
        this.name = name;
    }
}
