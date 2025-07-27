package com.questandglory.services;

public interface ChatResponseDeserializer<T> {

    /**
     * Deserializes the AI response into a specific type.
     *
     * @param aiResponse AI response string to deserialize.
     * @return Deserialized object of type T.
     * @throws IllegalArgumentException                If <i>aiResponse</i> is {@code null} or empty.
     * @throws AiResponseDeserializationErrorException If the response cannot be deserialized into the expected type.
     */
    T deserialize(String aiResponse) throws IllegalArgumentException, AiResponseDeserializationErrorException;

}
