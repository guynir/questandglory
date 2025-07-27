package com.questandglory.engine.messages;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode
@ToString
public abstract class AbstractMessage<E extends Enum<E>> implements Serializable {

    @Getter
    protected final E messageType;

    protected AbstractMessage(E messageType) {
        this.messageType = messageType;
    }

}
