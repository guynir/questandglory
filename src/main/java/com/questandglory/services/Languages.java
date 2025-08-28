package com.questandglory.services;

import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

public class Languages {

    /**
     * Language descriptor for global English language.
     */
    public static final Language ENGLISH_LANGUAGE = new Language("eng", "English", "English", true);

    /**
     * Language descriptor for global Hebrew language.
     */
    public static final Language HEBREW_LANGUAGE = new Language("heb", "Hebrew","עברית", false);

    /**
     * Default language if non specified.
     */
    public static final Language DEFAULT_LANGUAGE = ENGLISH_LANGUAGE;

    public static final List<Language> LANGUAGES = Arrays.asList(ENGLISH_LANGUAGE, HEBREW_LANGUAGE);

    private static final Map<String, Language> LANGUAGES_BY_ISO_CODE = LANGUAGES.stream()
            .collect(Collectors.toMap(Language::isoCode, lang -> lang, (a, b) -> a));

    private static final Map<String, Language> LANGUAGES_BY_DISPLAY_NAME = LANGUAGES.stream()
            .collect(Collectors.toMap(l -> l.displayNameEnglish().toLowerCase(),
                    lang -> lang,
                    (a, b) -> a));

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

    public static boolean isRTLLanguage(Locale language) {
        return RTL_LANGUAGES.contains(language);
    }

    /**
     * Lookup a language by its ISO 3-characters code.
     *
     * @param isoCode ISO code to look by.
     * @return The language if found, or the {@code null} if language not found.
     */
    public static Language getByCode(String isoCode) {
        return LANGUAGES_BY_ISO_CODE.getOrDefault(isoCode, DEFAULT_LANGUAGE);
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

    /**
     * Lookup a language by its English display name.
     *
     * @param language Case-insensitive display name of the language.
     * @return Language if found, or {@code null} if not found.
     */
    public static Language getLanguage(String language) {
        Assert.hasText(language, "Language cannot be null or empty.");
        return LANGUAGES_BY_DISPLAY_NAME.get(language.toLowerCase());
    }
}
