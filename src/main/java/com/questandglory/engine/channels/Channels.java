package com.questandglory.engine.channels;

import com.questandglory.engine.messages.ServerMessage;
import lombok.Getter;

public class Channels {

    @Getter
    public final IncomingMessagesChannel incomingMessagesChannel;

    @Getter
    public final OutgoingMessagesChannel outgoingMessagesChannel;

    public Channels(IncomingMessagesChannel incomingMessagesChannel,
                    OutgoingMessagesChannel outgoingMessagesChannel) {
        this.incomingMessagesChannel = incomingMessagesChannel;
        this.outgoingMessagesChannel = outgoingMessagesChannel;
    }

    public void send(ServerMessage message) {
        outgoingMessagesChannel.sendMessage(message);
    }

}
