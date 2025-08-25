package com.questandglory.engine.statements;

import com.questandglory.engine.ChatObjectsManager;
import com.questandglory.engine.EngineFacade;
import com.questandglory.engine.constructs.Identifier;
import com.questandglory.engine.expressions.string.StringExpression;
import com.questandglory.parser.antlr.Location;
import com.questandglory.services.ChatHandler;

public class AskStatement extends Statement {

    private final Identifier target;

    private final String chatId;

    private final StringExpression message;

    public AskStatement(Location location, Identifier target, String chatId, StringExpression expression) {
        super(location);
        this.target = target;
        this.chatId = chatId;
        this.message = expression;
    }

    private static Boolean parseBoolean(String st) {
        Boolean result;
        st = st.trim().toLowerCase();
        while (st.endsWith(".")) {
            st = st.substring(0, st.length() - 1);
        }

        if (st.equals("true") || st.equals("1") || st.equals("yes")) {
            result = true;
        } else if (st.equals("false") || st.equals("0") || st.equals("no")) {
            result = false;
        } else {
            result = null;
        }

        return result;
    }

    @Override
    protected void handleInternal(EngineFacade facade) throws InterruptedException {
        // Helps to access chat handlers.
        ChatObjectsManager manager = new ChatObjectsManager(facade);

        ChatHandler<String> handler = manager.getChatHandler(chatId);
        String userMessage = message.evaluate(facade);
        String response = handler.postUserMessage(userMessage);

        String variableName = target.getName();
        Class<?> variableType = facade.state().getVariableType(variableName);
        Object value;
        if (variableType.equals(String.class)) {
            value = response;
        } else if (variableType.equals(Integer.class)) {
            response = response.trim();
            try {
                value = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Non-integer value: " + response);
            }
        } else if (variableType.equals(Boolean.class)) {
            value = parseBoolean(response);
            if (value == null) {
                throw new IllegalStateException("Non-boolean value: " + response);
            }
        } else {
            throw new IllegalStateException("Unsupported variable type: " + variableType.getSimpleName());
        }

        facade.state().setVariable(target.getName(), value);
    }
}
