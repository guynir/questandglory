package com.questandglory.engine.channels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.questandglory.engine.messages.ServerMessage;
import lombok.Getter;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;
import java.util.Map;

public class OutgoingMessagesChannel {

    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper = new ObjectMapper();

    @Getter
    private final String clientQueueId;

    public OutgoingMessagesChannel(SimpMessagingTemplate template, String gamePlayId) {
        this.template = template;
        this.clientQueueId = "/queue/" + gamePlayId + "/client";
    }

    public void sendMessage(ServerMessage message) {
        try {
            Map<String, Object> headers = new HashMap<>();
            headers.put("content-type", "application/json");
            template.convertAndSend(clientQueueId, message, headers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
