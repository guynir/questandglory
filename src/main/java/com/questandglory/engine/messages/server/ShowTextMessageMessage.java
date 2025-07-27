package com.questandglory.engine.messages.server;

import com.questandglory.engine.messages.ServerMessage;
import com.questandglory.engine.messages.ServerMessageTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ShowTextMessageMessage extends ServerMessage implements Serializable {

    @Getter
    private final String message;

    public ShowTextMessageMessage(String componentKey, String message) {
        super(ServerMessageTypeEnum.SHOW_TEXT_MESSAGE, componentKey);
        this.message = message;
    }
}
