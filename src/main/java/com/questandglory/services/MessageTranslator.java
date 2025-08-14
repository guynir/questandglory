package com.questandglory.services;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;

import java.util.HashMap;
import java.util.Map;

import static dev.langchain4j.data.message.UserMessage.userMessage;

/**
 * A message translation that users AI model to translate messages from one language to another. The implementation
 * also maintains an in-memory cache of translations to avoid redundant calls to the AI model for the same message.
 *
 * @author Guy Raz Nir
 * @since 2025/07/19
 */
public class MessageTranslator {

    /**
     * Chat model to use for translations.
     */
    private final ChatModel model;
    /**
     * In-memory cache of translations, mapping TranslationKey to the translated message.
     */
    private final Map<TranslationKey, String> translations = new HashMap<>();

    /**
     * Class constructor.
     *
     * @param model The chat model to use for translations.
     */
    public MessageTranslator(ChatModel model) {
        this.model = model;
    }

    /**
     * Translates a given message to the specified target language using the AI model. If the translation is already
     * in the cache, it retrieves it from there instead of calling the model again.
     *
     * @param message        Message to be translated.
     * @param targetLanguage Target language to translate the message into. Must be a supported language.
     * @return Translated message in the target language.
     * @throws IllegalArgumentException If either the message is null or empty, or the target language is not supported.
     */
    public String translate(String message, String targetLanguage) throws IllegalArgumentException {
        if (!Languages.isSupportedLanguage(targetLanguage)) {
            throw new IllegalArgumentException("Unsupported language: " + targetLanguage);
        }

        TranslationKey key = new TranslationKey(message, targetLanguage);
        String translatedMessage = translations.get(key);
        if (translatedMessage == null) {
            ChatResponse response = model.chat(userMessage("Translate the following text to " + targetLanguage + ", do not add any further details:" + message));
            translatedMessage = response.aiMessage().text();
            translations.put(key, translatedMessage);
        }
        return translatedMessage;
    }

    /**
     * A record to represent a translation key, which consists of a message and a target language. Used to quickly
     * lookup translations in memory cache.
     *
     * @param message        Original message to be translated.
     * @param targetLanguage Target language for the translation.
     */
    private record TranslationKey(String message, String targetLanguage) {
    }
}
