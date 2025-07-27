package com.questandglory.template;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.TimeZone;

public class FreemarkerStringTemplateEngine implements StringTemplateEngine {

    private final StringTemplateLoader templateLoader = new StringTemplateLoader();
    private final Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);

    public FreemarkerStringTemplateEngine() {
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);
        configuration.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
        configuration.setTemplateLoader(templateLoader);
    }

    @Override
    public String render(String template, Map<String, Object> model) {
        if (templateLoader.findTemplateSource(template) == null) {
            templateLoader.putTemplate(template, template);
        }
        try {
            Template freeMarkerTemplate = configuration.getTemplate(template);
            StringWriter writer = new StringWriter();
            freeMarkerTemplate.process(model, writer);
            return writer.toString();
        } catch (IOException | TemplateException ex) {
            throw new RuntimeException(ex);
        }
    }
}
