package com.questandglory.engine.messages.client;

import com.questandglory.engine.messages.ClientMessage;
import com.questandglory.engine.messages.ClientMessageTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class TextInputMessage extends ClientMessage implements Serializable {

    private String text;

    public TextInputMessage() {
        super(ClientMessageTypeEnum.TEXT_INPUT);
    }

    public TextInputMessage(String text) {
        this();
        this.text = text;
    }
}
