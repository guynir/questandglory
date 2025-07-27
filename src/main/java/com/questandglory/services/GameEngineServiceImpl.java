package com.questandglory.services;

import com.questandglory.engine.*;
import com.questandglory.engine.channels.ChannelFactory;
import com.questandglory.engine.conditions.BooleanValueExpression;
import com.questandglory.engine.conditions.ContextValueExpression;
import com.questandglory.engine.conditions.EqualsCondition;
import com.questandglory.engine.conditions.StringValueExpression;
import com.questandglory.utils.GlobalIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameEngineServiceImpl implements GameEngineService {

    private final ChannelFactory channelFactory;

    private final Map<String, GameEngine> enginesMap = new HashMap<>();

    private final ChatService chatService;

    private final Logger logger = LoggerFactory.getLogger(GameEngineServiceImpl.class);

    public GameEngineServiceImpl(ChannelFactory channelFactory, ChatService chatService) {
        this.channelFactory = channelFactory;
        this.chatService = chatService;
    }

    @Override
    public GameEngine createGameEngine() {
        int stepLine = 0;
        List<GameStep> steps = new ArrayList<>();
        steps.add(new MessageGameStep(++stepLine, "Welcome to the guessing game!"));
        steps.add(new MessageGameStep(++stepLine, ""));
        steps.add(new MessageGameStep(++stepLine, "This is an AI-based game where you will guess the name of a famous person."));
        steps.add(new MessageGameStep(++stepLine, "You can answer or write free-style. The game will understand your input and respond accordingly."));
        steps.add(new MessageGameStep(++stepLine, "You can even answer in your native language (e.g.: נעים להכיר - שמי הוא ג'ון), but the AI will respond in English."));
        steps.add(new MessageGameStep(++stepLine, ""));

        //
        // Query for the user's name
        //
        steps.add(new LabelGameStep(++stepLine, "ASK_NAME"));
        steps.add(new MessageGameStep(++stepLine, "What is your name?"));
        steps.add(new MessageGameStep(++stepLine, "(you can provide your name directly or answer as a human being would do, e.g.: \"My name is John\")"));
        steps.add(new InputGameStep(++stepLine, "inputName"));
        String prompt = """
                Given the following user input:
                ${inputName}
                Generate a JSON response with the following attributes:
                - firstName: The user's first name.
                - lastName: The user's last name.
                - hasName: A boolean indicating if the user has provided a name or not.
                The response must be a valid JSON object with the attributes mentioned above.
                """;
        steps.add(new ChatGameStep(++stepLine, null, prompt, "firstName", "hasName"));
        steps.add(new OnConditionGotoGameStep(++stepLine,
                new EqualsCondition(new ContextValueExpression("hasName"), new BooleanValueExpression(true)),
                "TRANSLATE_NAME"));
        steps.add(new MessageGameStep(++stepLine, "I could not understand your name, please try again."));
        steps.add(new GotoGameStep(++stepLine, "ASK_NAME"));

        // Start the guessing game.
        steps.add(new LabelGameStep(++stepLine, "TRANSLATE_NAME"));
        steps.add(new TranslationGameStep(++stepLine, Locale.ENGLISH, "${firstName}", "firstName"));

        steps.add(new LabelGameStep(++stepLine, "START_GAME"));
        steps.add(new MessageGameStep(++stepLine, ""));
        steps.add(new MessageGameStep(++stepLine, "Welcome to the game ${firstName}!"));
        steps.add(new MessageGameStep(++stepLine, ""));
        steps.add(new MessageGameStep(++stepLine, "The game is simple: You need to guess the name of a famous person. You cannot ask for his or her name directly, but you can ask questions about the person."));

        steps.add(new LabelGameStep(++stepLine, "USER_ANSWER"));
        steps.add(new InputGameStep(++stepLine, "userAnswer"));
        String systemPrompt = """
                You are playing a game with a user, where the user has to guess the name of a famous person - George Washington.
                You are not allowed to reveal the name of the person.
                You can provide hints or clues only if the user requests. You should not suggest hints by your own.
                All your responses should be a JSON object with the following structure:
                {
                    "answer": "your answer",
                    "successful": "'true' if the user guessed the name of the person, 'false' otherwise",
                    "is_out_of_scope": "'true' if the user asked a question that is not related to the person in context, 'false' otherwise"
                }
                """;
        prompt = "${userAnswer}";
        steps.add(new ChatGameStep(++stepLine, systemPrompt, prompt, "successful", "answer", "is_out_of_scope"));
        steps.add(new MessageGameStep(++stepLine, "${answer}"));
        steps.add(new OnConditionGotoGameStep(++stepLine,
                new EqualsCondition(new ContextValueExpression("successful"), new StringValueExpression("true")),
                "END"));
        steps.add(new GotoGameStep(++stepLine, "USER_ANSWER"));

        steps.add(new LabelGameStep(++stepLine, "END"));
        steps.add(new MessageGameStep(++stepLine, "Nicely done ! That's it for now. Bye !"));

        String gamePlayId = GlobalIdGenerator.generateId();
        GameEngine engine = new GameEngine(steps, channelFactory.createChannels(gamePlayId), gamePlayId, chatService);
        enginesMap.put(gamePlayId, engine);
        return engine;
    }

    @Override
    public GameEngine findGameEngine(String gamePlayId) {
        GameEngine engine = enginesMap.get(gamePlayId);
        if (engine == null) {
            throw new IllegalStateException("No game engine found for ID: " + gamePlayId);
        }
        return engine;
    }
}
