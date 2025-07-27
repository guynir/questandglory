package com.questandglory.engine.messages.server;

import com.questandglory.engine.messages.ServerMessage;
import com.questandglory.engine.messages.ServerMessageTypeEnum;

import java.io.Serializable;

public class RemoveComponentMessage extends ServerMessage implements Serializable {

    public RemoveComponentMessage(String componentKey) {
        super(ServerMessageTypeEnum.REMOVE_COMPONENT, componentKey);
    }
}
