package com.questandglory.engine.messages;

public class InputMessage extends ServerMessage {

    public InputMessage(String id) {
        super(ServerMessageTypeEnum.TEXT_INPUT);
    }

}
