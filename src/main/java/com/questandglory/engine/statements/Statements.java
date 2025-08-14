package com.questandglory.engine.statements;

import com.questandglory.engine.EngineFacade;
import com.questandglory.parser.antlr.Location;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Statements extends Statement implements Iterable<Statement> {

    @Getter
    private final List<Statement> statements = new ArrayList<>();

    public Statements(Location location) {
        super(location);
    }

    public int size() {
        return statements.size();
    }

    public void add(Statement statement) {
        statements.add(statement);
    }

    @Override
    @NonNull
    public Iterator<Statement> iterator() {
        return statements.iterator();
    }

    @Override
    protected void handleInternal(EngineFacade facade) throws InterruptedException {
        for (Statement statement : statements) {
            statement.handleInternal(facade);
        }
    }
}
