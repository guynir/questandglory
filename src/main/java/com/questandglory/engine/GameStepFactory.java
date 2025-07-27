package com.questandglory.engine;

import java.util.Map;

@SuppressWarnings("unchecked")
public class GameStepFactory {

    public GameStep createStep(Map<String, Object> definition) {
        int lineNumber;
        try {
            lineNumber = parseAsInteger(ensureValueExist(definition, "line"));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Could not extract line number for step definition.", ex);
        }

        String type = ensureValueExist(definition, "type", String.class);
        Map<String, Object> parsedDefinition = ensureValueExist(definition, "parsed", Map.class);
        switch (type) {
            case "messageStatement" -> {
                return createMessageGameStep(parsedDefinition);
            }

            case "gotoStatement" -> {
                return createGotoGameStep(parsedDefinition);
            }

            case "labelStatement" -> {
                return createLabelGameStep(parsedDefinition);
            }

            case "defineVariable" -> {
                return createDefineVariableGameStep(parsedDefinition);
            }

            case "functionCall" -> {
                return createFunctionCallGameStep(parsedDefinition);
            }

            default -> {
                throw new IllegalArgumentException("Unrecognized step type: " + type);
            }
        }
    }

    protected MessageGameStep createMessageGameStep(Map<String, Object> parsedDefinition) {
        @SuppressWarnings("unchecked")
        Map<String, String> actualDefinition = ensureValueExist(parsedDefinition, "expression", Map.class);
        String message = ensureValueExist(parsedDefinition, "message", String.class);
        return new MessageGameStep(0, message);
    }

    protected DefineVariableGameStep createDefineVariableGameStep(Map<String, Object> parsedDefinition) {
        String variableName = ensureValueExist(parsedDefinition, "identifier", String.class);
        String rawVariableType = ensureValueExist(parsedDefinition, "type", String.class);

        VariableTypeEnum variableType;
        switch (rawVariableType) {
            case "string" -> variableType = VariableTypeEnum.STRING;
            case "integer" -> variableType = VariableTypeEnum.INTEGER;
            case "boolean" -> variableType = VariableTypeEnum.BOOLEAN;
            default -> throw new IllegalArgumentException("Unsupported variable type: " + rawVariableType);
        }

        return new DefineVariableGameStep(0, variableName, variableType);
    }

    protected LabelGameStep createLabelGameStep(Map<String, Object> parsedDefinition) {
        String labelName = ensureValueExist(parsedDefinition, "label", String.class);
        return new LabelGameStep(0, labelName);
    }

    protected GotoGameStep createGotoGameStep(Map<String, Object> parsedDefinition) {
        String labelName = ensureValueExist(parsedDefinition, "label", String.class);
        return new GotoGameStep(0, labelName);
    }

    protected FunctionCallGameStep createFunctionCallGameStep(Map<String, Object> parsedDefinition) {
        String targetVariableName = ensureValueExist(parsedDefinition, "identifier", String.class);
        String functionName = ensureValueExist(parsedDefinition, "functionName", String.class);
        String[] arguments = ensureValueExist(parsedDefinition, "arguments", String[].class);
        return new FunctionCallGameStep(0, functionName, arguments, targetVariableName);
    }

    @SuppressWarnings("SameParameterValue")
    private static Object ensureValueExist(Map<String, Object> source, String propertyName) throws IllegalArgumentException {
        return ensureValueExist(source, propertyName, Object.class);
    }

    private static <T> T ensureValueExist(Map<String, Object> source, String propertyName, Class<T> expectedType) throws IllegalArgumentException {
        Object value = source.get(propertyName);
        if (value == null) {
            throw new IllegalArgumentException("Missing '" + propertyName + "' property.");
        }

        if (expectedType != null && !expectedType.isInstance(value)) {
            throw new IllegalArgumentException("Expected '" + propertyName + "' property to be of type '" + value.getClass().getName() + "'.");
        }

        //noinspection unchecked
        return (T) value;
    }


    private static int parseAsInteger(Object value) throws IllegalArgumentException {
        switch (value) {
            case Integer i -> {
                return i;
            }
            case String s -> {
                try {
                    return Integer.parseInt(s);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Non-integer value: '" + value + "'.", ex);
                }
            }
            case Long l -> {
                return l.intValue();
            }
            case null, default -> throw new IllegalArgumentException("Non-integer value: '" + value + "'.");
        }
    }
}
