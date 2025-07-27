package com.questandglory.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Assert;

@SuppressWarnings("ClassCanBeRecord")
public class JsonChatResponseDeserializer<T> implements ChatResponseDeserializer<T> {

    protected final Class<T> clazz;

    protected final ObjectMapper objectMapper;

    public JsonChatResponseDeserializer(Class<T> clazz) {
        this(clazz, null);
    }

    public JsonChatResponseDeserializer(Class<T> clazz, ObjectMapper objectMapper) {
        this.clazz = clazz;
        this.objectMapper = objectMapper != null ? objectMapper : new ObjectMapper();
    }

    @Override
    public T deserialize(String aiResponse) throws IllegalArgumentException, AiResponseDeserializationErrorException {
        Assert.hasText(aiResponse, "AiResponse must not be null or empty");

        //
        // Remove JSON code block markers if present, e.g.:
        //      ```json
        //      {
        //          "key": "value"
        //      }
        //      ```
        // to:
        //      {
        //          "key": "value"
        //      }
        //
        final String JSON_PREFIX = "```json";
        final String JSON_SUFFIX = "```";
        if (aiResponse.startsWith(JSON_PREFIX) && aiResponse.endsWith(JSON_SUFFIX)) {
            aiResponse = aiResponse.substring(JSON_PREFIX.length(), aiResponse.length() - JSON_SUFFIX.length()).trim();
        }

        try {
            return objectMapper.readValue(aiResponse, clazz);
        } catch (Exception ex) {
            throw new AiResponseDeserializationErrorException("Failed to deserialize AI response.", ex);
        }
    }
}
