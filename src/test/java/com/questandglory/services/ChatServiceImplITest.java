package com.questandglory.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.questandglory.ApplicationConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class, ChatServiceImpl.class})
public class ChatServiceImplITest {

    @Autowired
    private ChatServiceImpl chatService;

    private MessageTranslator translator;

    public record Response(String answer,
                           boolean successful,
                           boolean is_out_of_scope) {
    }

    @BeforeEach
    public void setUp() {
        translator = chatService.createMessageTranslator();
    }

    @Test
    public void testCreateSimpleChatMemory() throws JsonProcessingException {
        ChatHandler<Response> chat = chatService.createChatMemory(new JsonChatResponseDeserializer<>(Response.class));
        System.out.println(chat.getChatId());

        chat.setSystemMessage("""
                You are playing a game with a user, where the user has to guess the name of a famous person - George Washington.
                You are not allowed to reveal the name of the person.
                You can provide hints or clues only if the user requests. You should not suggest hints by your own.
                All your responses should be a JSON object with the following structure:
                {
                    "answer": "your answer",
                    "successful": "'true' if the user guessed the name of the person, 'false' otherwise",
                    "is_out_of_scope": "'true' if the user asked a question that is not related to the person in context, 'false' otherwise"
                }
                """);

        postUserMessage(chat, "What's the famous person's name ?");

        chat.setResponseLanguage("Hebrew");
        postUserMessage(chat, "Is he still alive ?");
        postUserMessage(chat, "Is he a black person ?");
        postUserMessage(chat, "Is it George Orwell ?");
        postUserMessage(chat, "Did I ask if the person was black ?");
        postUserMessage(chat, "How can I make a mushroom soup ?");
        postUserMessage(chat, "Is it George Washington?");
    }

    private void postUserMessage(ChatHandler<Response> handler, String message) {
        System.out.println("User: " + message);
        Response answer = handler.postUserMessage(message);

        if (answer.successful) {
            System.out.println(translate("The user guessed the name of the person!"));
        } else {
            System.out.println("AI  : " + answer.answer);

            if (answer.is_out_of_scope) {
                System.out.println(translate("The user asked a question that is not related to the game.'"));
            }
        }
    }

    private String translate(String text) {
        return translator.translate(text, "Hebrew");
    }

    @Test
    public void testShouldParseScript() {
        ScriptParser parser = chatService.createScriptParser();
        String program = """
                message "Hi ! What is your name ?";
                var name_input as string;
                var name as boolean;
                let name_input = read();
                let name = chat("Extract the name of a person from the following text: " + name_input + " or null if no name is provided.");
                message "Welcome ${name} to the game !";
                goto END;

                END:
                message "The end !";
                """;

        System.out.println(program);
        ScriptParser.ScriptParseResult result = parser.parse(program);
        System.out.println(result);

        if (!result.errors().isEmpty()) {
            System.out.println("ERROR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(result.errors());
        }
    }
}
