package com.questandglory.engine;

import com.questandglory.engine.channels.Channels;
import com.questandglory.services.ChatService;
import com.questandglory.services.MessageTranslator;
import com.questandglory.template.FreemarkerStringTemplateEngine;
import com.questandglory.template.StringTemplateEngine;
import lombok.Data;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Data
public class EngineExecutionContext {

    private final Channels channels;
    private final List<GameStep> steps;
    private final Map<String, Object> variables;
    private GameStep nextStep;
    private final ChatService chatService;
    private final StringTemplateEngine localTemplateEngine = new FreemarkerStringTemplateEngine();
    private final MessageTranslator messageTranslator;

    public EngineExecutionContext(Channels channels,
                                  List<GameStep> steps,
                                  Map<String, Object> variables,
                                  GameStep nextStep,
                                  ChatService chatService) {
        this.channels = channels;
        this.steps = steps;
        this.variables = variables;
        this.nextStep = nextStep;
        this.chatService = chatService;
        this.messageTranslator = chatService.createMessageTranslator();
    }

    public String render(String template) {
        return localTemplateEngine.render(template, variables);
    }

    @SuppressWarnings("unchecked")
    public <T> T getVariable(String name) {
        T value = (T) variables.get(name);
        if (value == null) {
            throw new IllegalStateException("Variable '" + name + "' not found in context.");
        }
        return value;
    }

    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    public String translate(String message, Locale targetLanguage) {
        return messageTranslator.translate(message, targetLanguage.getDisplayLanguage());
    }
}
