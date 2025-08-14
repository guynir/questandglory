package com.questandglory.services;

import java.util.*;
import java.util.stream.Collectors;

public class Languages {

    /**
     * Set of languages that are written from right to left (RTL).
     */
    private static final Set<Locale> RTL_LANGUAGES = createRTLLanguagesSet();
    /**
     * Maps a language name to its corresponding Locale.
     */
    private static final Map<String, Locale> LANGUAGES_MAP = createLanguagesMap();
    /**
     * Set of supported languages in their display names.
     */
    private static final Set<String> SUPPORTED_LANGUAGES_MAP = LANGUAGES_MAP.keySet();
    /**
     * Set of supported languages in their display names, all in lower case.
     */
    private static final Set<String> SUPPORTED_LANGUAGES_MAP_LOWER_CASE = SUPPORTED_LANGUAGES_MAP.stream().map(String::toLowerCase).collect(Collectors.toSet());

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isSupportedLanguage(String language) {
        return SUPPORTED_LANGUAGES_MAP_LOWER_CASE.contains(language.trim().toLowerCase());
    }

    public static boolean isRTLLanguage(String language) {
        return RTL_LANGUAGES.contains(Locale.forLanguageTag(language));
    }

    private static Map<String, Locale> createLanguagesMap() {
        Map<String, Locale> languagesMap = new TreeMap<>();
        for (String language : Locale.getISOLanguages()) {
            Locale locale = Locale.forLanguageTag(language);
            languagesMap.put(locale.getDisplayLanguage(), locale);
        }
        return languagesMap;
    }

    private static Set<Locale> createRTLLanguagesSet() {
        Set<Locale> languages = new HashSet<>();

        languages.add(Locale.forLanguageTag("he")); // Hebrew
        languages.add(Locale.forLanguageTag("yi")); // Yiddish
        languages.add(Locale.forLanguageTag("arc")); // Aramaic
        languages.add(Locale.forLanguageTag("ar")); // Arabic
        languages.add(Locale.forLanguageTag("az")); // Azerbaijani
        languages.add(Locale.forLanguageTag("fa")); // Persian (Farsi)
        languages.add(Locale.forLanguageTag("ur")); // Urdu
        return languages;
    }

}
