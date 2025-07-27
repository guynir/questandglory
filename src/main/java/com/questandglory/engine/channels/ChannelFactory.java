package com.questandglory.engine.channels;

import com.questandglory.utils.GlobalIdGenerator;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChannelFactory {

    private final SimpMessagingTemplate template;

    private final Map<String, Channels> channelsMap = new ConcurrentHashMap<>();

    public ChannelFactory(SimpMessagingTemplate template) {
        this.template = template;
    }

    public Channels createChannels(String gamePlayId) {
        IncomingMessagesChannel incomingMessagesChannel = new IncomingMessagesChannel();
        OutgoingMessagesChannel outgoingMessagesChannel = new OutgoingMessagesChannel(template, gamePlayId);
        Channels channels = new Channels(incomingMessagesChannel, outgoingMessagesChannel);
        channelsMap.put(gamePlayId, channels);
        return channels;
    }

    public Channels findChannels(String instanceId) {
        Channels channels = channelsMap.get(instanceId);
        if (channels == null) {
            throw new IllegalStateException("No channels for instance " + instanceId);
        }
        return channels;
    }
}
