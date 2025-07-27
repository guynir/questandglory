package com.questandglory.template;

import java.util.Map;

public interface StringTemplateEngine {

    String render(String template, Map<String, Object> model);

}
