package com.questandglory.web.controllers;

import com.questandglory.engine.channels.ChannelFactory;
import com.questandglory.engine.channels.Channels;
import com.questandglory.engine.messages.ClientMessage;
import com.questandglory.engine.messages.client.TextInputMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ClientMessagesController {

    private static final Logger logger = LoggerFactory.getLogger(ClientMessagesController.class);
    private final ChannelFactory channelFactory;

    public ClientMessagesController(ChannelFactory channelFactory) {
        this.channelFactory = channelFactory;
    }


    @MessageMapping("/messages")
    public void accept() {
        logger.info("Incoming message.");
    }

    @MessageMapping("/messages/{instanceId}/{mailboxId}")
    public void send(ClientMessage message, @DestinationVariable String instanceId, @DestinationVariable String mailboxId) {
        TextInputMessage inputMessage = (TextInputMessage) message;
        logger.info("Incoming message: instanceId={}, mailboxId={}.", instanceId, mailboxId);

        Channels channels = channelFactory.findChannels(instanceId);
        channels.incomingMessagesChannel.postMessage(mailboxId, inputMessage);
    }

    @MessageMapping("/queue/{instanceId}/{mailboxId}")
    public void send2(ClientMessage message, @DestinationVariable String instanceId, @DestinationVariable String mailboxId) {
        TextInputMessage inputMessage = (TextInputMessage) message;
        logger.info("Incoming message for queue: instanceId={}, mailboxId={}.", instanceId, mailboxId);

        Channels channels = channelFactory.findChannels(instanceId);
        channels.incomingMessagesChannel.postMessage(mailboxId, inputMessage);
    }

}
