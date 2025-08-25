package com.questandglory.services;

import com.questandglory.utils.SecureRandomIdGenerator;
import com.questandglory.utils.StringIdGenerator;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O;

@Service
public class ChatServiceImpl implements ChatService {

    private static final int MAX_MESSAGES = 1000;
    private final StringIdGenerator idGenerator = new SecureRandomIdGenerator();

    private final ChatMemoryStore chatStore = new InMemoryChatMemoryStore();

    public ChatModel model;
    @Value("${OPENAI_API_KEY}")
    private String OPENAI_API_KEY;

    @PostConstruct
    public void setup() {
        model = OpenAiChatModel.builder()
                .apiKey(OPENAI_API_KEY)
                .modelName(GPT_4_O)
                .build();
    }

    @Override
    public <T> ChatHandler<T> createChatMemory(ChatResponseDeserializer<T> deserializer) {
        String chatId = idGenerator.generate();

        ChatMemory chatMemory = new MessageWindowChatMemory.Builder()
                .id(chatId)
                .chatMemoryStore(chatStore)
                .maxMessages(MAX_MESSAGES)
                .build();

        return new ChatHandler<>(chatId, model, chatMemory, deserializer);
    }

    @Override
    public ChatHandler<String> createSimpleChatMemory() {
        return createChatMemory(new SimpleChatResponseDeserializer());
    }

    @Override
    public MessageTranslator createMessageTranslator() {
        return new MessageTranslator(model);
    }

    @Override
    public ScriptParser createScriptParser() {
        ClassPathResource resource = new ClassPathResource("/language.ebnf");
        String ebnfContent;
        try {
            ebnfContent = resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String message = String.format("""
                You are a language parser based on EBNF grammar.
                The grammar is as follows:
                
                %s
                
                The generated output must be a valid JSON object.
                The input code validation must be strict, meaning that the it must adhere to the grammar rules without any deviations.
                Each line should be parsed and represented as a separated object in a JSON array.
                The following fields must be included in each object:
                - 'line': The line number in the original script.
                - 'type': The type of the line (one of: message_statement, goto_statement, label_statement, define_variable, function_call).
                - 'content': The content of the line.
                - 'errors': An array of errors found in the line.
                - A parsed representation of the line content if no errors are found.
                Expressions should be broken down into their components, such as function calls, variable definitions, and control flow statements.
                Do not try to correct the input code.
                Include a section that list all errors, where each error contains a line number and an error message.
                Do not add semicolumn at the end of each line.
                """, ebnfContent);
        return new ScriptParser(model, ebnfContent);

    }

    public void setOpenAPIKey(String openAPIKey) {
        this.OPENAI_API_KEY = openAPIKey;
    }
}
