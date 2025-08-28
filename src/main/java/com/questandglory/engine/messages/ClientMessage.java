package com.questandglory.engine.messages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.questandglory.engine.messages.client.SetLanguageMessage;
import com.questandglory.engine.messages.client.TextInputMessage;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "messageType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextInputMessage.class, name = "TEXT_INPUT"),
        @JsonSubTypes.Type(value = SetLanguageMessage.class, name = "SET_LANGUAGE")
})
public abstract class ClientMessage extends AbstractMessage<ClientMessageTypeEnum> implements Serializable {

    protected ClientMessage(ClientMessageTypeEnum messageType) {
        super(messageType);
    }

}
