package com.questandglory.engine;

import com.questandglory.engine.channels.Mailbox;
import com.questandglory.engine.messages.ClientMessage;
import com.questandglory.engine.messages.ServerMessage;
import com.questandglory.services.ChatHandler;

import java.util.Locale;

/**
 * Engine facade expose set of operations that allows a game step to access an outside resources and communicate with
 * the "outer world".
 *
 * @author Guy Raz Nir
 * @since 2025/07/31
 */
public interface EngineFacade {

    /**
     * Provide access to the game state (variables and such, which are available in the given context).
     *
     * @return Game state.
     */
    GameState state();

    /**
     * Provide access to the engine execution context. This context contains state which is internal to the engine,
     * and should not be accessible from the game step.
     *
     * @return Engine state.
     */
    ExecutionState executionState();

    /**
     * Render a template using the {@link #state() game state} as a context for the template.
     *
     * @param template Template to render.
     * @return Rendered template as a string.
     */
    String render(String template);

    /**
     * Translate a given message to the target language.
     *
     * @param message        Message to translate.
     * @param targetLanguage Target language to translate the message to.
     * @return Translated message as a string.
     */
    String translate(String message, Locale targetLanguage);

    /**
     * Send a message to a specific queue or topic. Typically, a queue is associated with a specific game play client.
     *
     * @param message Message to send.
     */
    void sendMessage(ServerMessage message);

    Mailbox createMailbox();

    void postMessage(String mailboxId, ClientMessage message);

    void closeMailbox(Mailbox mailbox);

    <T> ChatHandler<T> creatChat(Class<T> type);

    ChatHandler<String> createSimpleChat();
}