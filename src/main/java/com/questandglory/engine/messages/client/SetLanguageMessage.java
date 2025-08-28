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
public class SetLanguageMessage extends ClientMessage implements Serializable {

    private final String isoCode;

    protected SetLanguageMessage(String isoCode) {
        super(ClientMessageTypeEnum.SET_LANGUAGE);
        this.isoCode = isoCode;
    }
}
