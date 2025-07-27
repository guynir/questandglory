package com.questandglory.engine;

import com.questandglory.services.ChatHandler;
import com.questandglory.services.JsonChatResponseDeserializer;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Map;

public class ChatGameStep extends GameStep implements Serializable {

    private final String systemMessage;
    private final String prompt;
    private final String[] exposeAttributes;
    private ChatHandler<Map> chatHandler;

    public ChatGameStep(int lineNumber, String systemMessage, String prompt, String... exposeAttributes) {
        super(lineNumber, GameStepEnum.CHAT);
        this.systemMessage = systemMessage;
        this.prompt = prompt;
        this.exposeAttributes = exposeAttributes;
    }

    @Override
    public void prepare(EngineExecutionContext context) {
        super.prepare(context);
        chatHandler = context.getChatService().createChatMemory(new JsonChatResponseDeserializer<>(Map.class));
        if (StringUtils.hasText(systemMessage)) {
            String renderedSystemMessage = context.render(systemMessage);
            chatHandler.setSystemMessage(renderedSystemMessage);
        }
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public GameStep execute(EngineExecutionContext context) throws InterruptedException {

        String renderedPrompt = context.render(this.prompt);

        Map<String, Object> response = chatHandler.postUserMessage(renderedPrompt);
        Map<String, Object> variables = context.getVariables();

        // Save the entire response in the variables map.
        variables.put("chatResponse", response);

        // For selected attributes, expose them in the game context.
        for (String attribute : exposeAttributes) {
            Object value = response.get(attribute);
            if (value != null) {
                variables.put(attribute, response.get(attribute));
            }
        }

        return context.getNextStep();
    }
}
