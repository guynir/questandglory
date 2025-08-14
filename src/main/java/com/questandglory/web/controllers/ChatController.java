package com.questandglory.web.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    @Value("${OPENAI_API_KEY:}")
    private String openApiKey;

    @ResponseBody
    @RequestMapping(path = "/status", produces = "text/plain")
    public String status() {
        return StringUtils.hasText(openApiKey) ? "OK" : "Open API key is missing.";
    }
}
