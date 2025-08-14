package com.questandglory.parser;

import com.questandglory.parser.antlr.ErrorDescription;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class CompilationErrors {

    private final List<ErrorDescription> errors = new LinkedList<>();

    public CompilationErrors() {
    }

    public void addError(ErrorDescription error) {
        errors.add(error);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<ErrorDescription> errors() {
        return errors;
    }

    public void sort() {
        errors.sort(Comparator.comparing(ErrorDescription::location));
    }

}
