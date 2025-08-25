package com.questandglory.language.parser;

import com.questandglory.engine.EngineFacade;
import com.questandglory.language.compiler.CompiledScript;
import com.questandglory.language.compiler.Compiler;
import com.questandglory.language.compiler.DefaultCompiler;
import com.questandglory.services.ChatServiceImpl;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Helpers {

    private static final Compiler COMPILER = new DefaultCompiler();

    public record ExecutionResult(CompiledScript compiled, EngineFacade facade) {
    }

    public static CompiledScript compile(String script) {
        return COMPILER.compile(script);
    }

    public static ExecutionResult execute(String script) throws IOException, InterruptedException {
        CompiledScript results = compile(script);

        TestableEngineFacade facade = new TestableEngineFacade();
        facade.setChatService(Helpers.createChatService());
        results.program().handle(facade);

        return new ExecutionResult(results, facade);
    }

    public static ChatServiceImpl createChatService() throws IOException {
        Properties properties = loadLocalProperties();
        ChatServiceImpl service = new ChatServiceImpl();
        service.setOpenAPIKey(properties.getProperty("OPENAI_API_KEY"));
        service.setup();
        return service;
    }

    protected static Properties loadLocalProperties() throws IOException {
        ClassPathResource classPathRoot = new ClassPathResource("/");

        // Assuming the project classpath in runtime is: "<projectRoot>/build/classes/java/main".
        // We need to get the 4th parent directory to reach the project root.
        File projectRoot = classPathRoot.getFile().getParentFile().getParentFile().getParentFile().getParentFile();
        File dotEnvFile = new File(projectRoot, ".env");
        try (InputStream in = new FileInputStream(dotEnvFile)) {
            Properties props = new Properties();
            props.load(in);
            return props;
        }

    }

}
