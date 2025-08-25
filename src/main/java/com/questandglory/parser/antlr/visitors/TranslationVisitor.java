package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.constructs.Identifier;
import com.questandglory.engine.expressions.string.LiteralStringValueExpression;
import com.questandglory.engine.expressions.string.StringExpression;
import com.questandglory.parser.CompilationException;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.Location;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class TranslationVisitor extends AbstractLanguageVisitor<TranslateStatement> {

    private final Set<String> SUPPRTED_LANGUAGES = createLanguageSet();

    private static Set<String> createLanguageSet() {
        return Arrays
                .stream(Locale.getAvailableLocales())
                .map(Locale::getDisplayLanguage)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    @Override
    public TranslateStatement visitStringLiteral(LanguageParser.StringLiteralContext ctx) {
        return super.visitStringLiteral(ctx);
    }

    @Override
    public TranslateStatement visitTranslateStatement(LanguageParser.TranslateStatementContext ctx) {
        LiteralStringValueExpression targetLanguage = factory.literalStringExpression(ctx.targetLanguage);
        String language = targetLanguage.getValue();
        Identifier targetVariable = factory.existingVariable(ctx.target);
        StringExpression text = factory.stringExpression(ctx.string);

        if (!SUPPRTED_LANGUAGES.contains(language.toLowerCase())) {
            throw new CompilationException("Unsupported language for translation: " + language, Location.from(ctx.targetLanguage));
        }

        Locale locale = Locale.forLanguageTag(language);

        return new TranslateStatement(Location.from(ctx), targetVariable, locale, text);
    }
}
