package com.questandglory.engine.messages;

import lombok.Getter;

import java.io.Serializable;

public abstract class ServerMessage extends AbstractMessage<ServerMessageTypeEnum> implements Serializable {

    @Getter
    private final String componentKey;

    protected ServerMessage(ServerMessageTypeEnum messageType) {
        this(messageType, null);
    }

    protected ServerMessage(ServerMessageTypeEnum messageType, String componentKey) {
        super(messageType);
        this.componentKey = componentKey;
    }

}
