package com.questandglory.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.response.ChatResponse;

import java.util.List;
import java.util.Map;

import static dev.langchain4j.data.message.UserMessage.userMessage;

public class ScriptParser {

    private final ChatModel model;
    private final String ebnfDefinition;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public record LineErrors(int lineNumber, String message) {
    }

    public record ScriptParseResult(String contents,
                                    List<Map<String, Object>> parsedScript,
                                    List<LineErrors> errors) {
    }

    public ScriptParser(ChatModel model, String ebnfDefinition) {
        this.model = model;
        this.ebnfDefinition = ebnfDefinition;
    }

    @SuppressWarnings("unchecked")
    public ScriptParseResult parse(String program) {
        SystemMessage systemMessage = SystemMessage.systemMessage("You are a strict language parser based on EBNF grammar.");
        String requestContents = """
                You are a parser for the following EBNF grammar:
                
                %s
                
                The generated output must be a valid JSON object.
                The input code validation must be strict, meaning that the it must adhere to the grammar rules without any deviations.
                Each line should be parsed and represented as a separated object in a JSON array.
                The following fields must be included in each object:
                - 'line': The line number in the original script.
                - 'type': The type of statement.
                - 'content': The content of the line.
                - 'errors': An array of errors found in the line.
                - A parsed representation of the line content if no errors are found.
                Expressions should be broken down into their components, such as function calls, variable definitions, and control flow statements.
                Do not try to correct the input code.
                Include a section that list all errors, where each error contains a line number and an error message.
                Do not add semicolumn at the end of each line.

                Here is the code:
                %s
                """.formatted(ebnfDefinition, program);

        UserMessage userMessage = UserMessage.userMessage(requestContents);

        ChatRequest request = ChatRequest.builder()
                .responseFormat(ResponseFormat.JSON)
                .temperature(0.0)
                .messages(systemMessage, userMessage)
                .build();
        ChatResponse response = model.chat(request);
        Map<String, List<?>> parsedResults;
        try {
            //noinspection unchecked
            parsedResults = (Map<String, List<?>>) objectMapper.readValue(response.aiMessage().text(), Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Extract parsed script and errors from AI response.
        List<Map<String, Object>> parsedScript = (List<Map<String, Object>>) parsedResults.get("lines");
        List<Map<String, Object>> errors = (List<Map<String, Object>>) parsedResults.get("errors");

        // Convert errors to strongly-typed objects.
        List<LineErrors> lineErrors = errors.stream().map(error -> {
            int lineNumber = (int) error.get("line");
            String message = (String) error.get("message");
            return new LineErrors(lineNumber, message);
        }).toList();

        return new ScriptParseResult(response.aiMessage().text(), parsedScript, lineErrors);
    }
}
