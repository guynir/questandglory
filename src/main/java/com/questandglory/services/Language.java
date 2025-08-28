package com.questandglory.services;

/**
 * Describe a language in the platform.
 *
 * @param isoCode            3-characters ISO code of the language.
 * @param displayNameEnglish Display name in English language (e.g., "Hebrew").
 * @param displayNameLocal   Display name in the local language (e.g., "עברית" for Hebrew).
 * @param ltr                {@code true} if language is left-to-right, {@code false} if right-to-left.
 */
public record Language(String isoCode,
                       String displayNameEnglish,
                       String displayNameLocal,
                       boolean ltr) {
}
