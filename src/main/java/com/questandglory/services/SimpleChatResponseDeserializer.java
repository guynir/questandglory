package com.questandglory.services;

import org.springframework.util.Assert;

/**
 * A simple implementation of the ChatResponseDeserializer that returns the AI response as a String.
 *
 * @author Guy Raz Nir
 * @since 2025/07/19
 */
public class SimpleChatResponseDeserializer implements ChatResponseDeserializer<String> {

    @Override
    public String deserialize(String aiResponse) throws IllegalArgumentException, AiResponseDeserializationErrorException {
        Assert.notNull(aiResponse, "AiResponse must not be null");
        Assert.hasText(aiResponse, "AiResponse must not be empty");

        return aiResponse;
    }
}
