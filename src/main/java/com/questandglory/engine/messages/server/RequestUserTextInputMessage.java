package com.questandglory.engine.messages.server;

import com.questandglory.engine.messages.ServerMessage;
import com.questandglory.engine.messages.ServerMessageTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RequestUserTextInputMessage extends ServerMessage implements Serializable {

    @Getter
    private final String responseMailboxId;

    public RequestUserTextInputMessage(String componentKey, String responseMailboxId) {
        super(ServerMessageTypeEnum.TEXT_INPUT, componentKey);
        this.responseMailboxId = responseMailboxId;
    }
}
