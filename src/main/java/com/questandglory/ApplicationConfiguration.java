package com.questandglory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;

/**
 * Application-wide configuration class.
 *
 * @author Guy Raz Nir
 * @since 2025/07/19
 */
@Configuration
public class ApplicationConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

    /**
     * <p>Configures a property source placeholder configurer that loads properties from a .env file located at the
     * project root.
     * </p>
     * Does nothing if the .env file is not found.
     *
     * @return A new configurer for property sources.
     * @throws IOException If an I/O error occurs while locating the .env file.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() throws IOException {
        final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();

        ClassPathResource classPathRoot = new ClassPathResource("/");

        if (!classPathRoot.getURI().getScheme().equals("jar")) {
            // Assuming the project classpath in runtime is: "<projectRoot>/build/classes/java/main".
            // We need to get the 4th parent directory to reach the project root.
            File projectRoot = classPathRoot.getFile().getParentFile().getParentFile().getParentFile().getParentFile();
            File dotEnvFile = new File(projectRoot, ".env");

            logger.info("Loading properties from: {}.", dotEnvFile.getAbsolutePath());
            if (!dotEnvFile.exists()) {
                logger.warn(".env file not found at: {}. No properties will be loaded.", dotEnvFile.getAbsolutePath());
            }

            configurer.setLocations(new FileSystemResource(dotEnvFile));
            configurer.setIgnoreResourceNotFound(true);
            configurer.setIgnoreUnresolvablePlaceholders(false);
        }

        return configurer;
    }
}
