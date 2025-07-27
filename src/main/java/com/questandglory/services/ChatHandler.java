package com.questandglory.services;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import lombok.Getter;
import org.springframework.util.StringUtils;

import static dev.langchain4j.data.message.SystemMessage.systemMessage;
import static dev.langchain4j.data.message.UserMessage.userMessage;

/**
 * <p>A chat handler encapsulates the logic for managing chat interactions, including LangChain4J chat memory and
 * associated model.
 * </p>
 * It also provides helping method for posting requests to the model.
 *
 * @author Guy Raz Nir
 * @since 2025/07/19
 */
public class ChatHandler<R> {

    @Getter
    private final String chatId;

    @Getter
    private final ChatModel model;

    @Getter
    private final ChatMemory chatMemory;

    private final ChatResponseDeserializer<R> deserializer;

    private String systemMessage = null;

    private String responseLanguage = "English";

    /**
     * Class constructor.
     *
     * @param chatId       A unique identifier for the chat session.
     * @param model        The chat model used for generating responses.
     * @param chatMemory   The chat memory instance that stores the conversation history.
     * @param deserializer The deserializer used to convert the chat model's response into a specific type.
     */
    public ChatHandler(String chatId,
                       ChatModel model,
                       ChatMemory chatMemory,
                       ChatResponseDeserializer<R> deserializer) {
        this.chatId = chatId;
        this.model = model;
        this.chatMemory = chatMemory;
        this.deserializer = deserializer;
    }

    /**
     * <p>Sets the system message for the chat memory. There is no more than 1 system message per chat memory.
     * </p>
     * A system message is a special type of message that provides context or instructions for the chat model. For
     * example, it can be used to set the role of the AI or provide specific instructions on how to respond to user.
     *
     * @param systemMessage System message to set for the chat memory.
     */
    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;

        if (!StringUtils.hasText(systemMessage)) {
            systemMessage = "";
        }

        systemMessage += "\n\n" + "All responses must be in " + responseLanguage + " language.";

        chatMemory.add(systemMessage(systemMessage));
    }

    public void setResponseLanguage(String responseLanguage) {
        if (!Languages.isSupportedLanguage(responseLanguage)) {
            throw new IllegalArgumentException("Unsupported response language: " + responseLanguage);
        }
        this.responseLanguage = responseLanguage;
        setSystemMessage(systemMessage);
    }

    public R postUserMessage(String message) {
        return postUserMessage(message, true);
    }

    public R postUserMessage(String message, boolean persistResponse) {
        chatMemory.add(userMessage(message));
        AiMessage answer = model.chat(chatMemory.messages()).aiMessage();
        if (persistResponse) {
            chatMemory.add(answer);
        }

        return deserializer.deserialize(answer.text());
    }


}
