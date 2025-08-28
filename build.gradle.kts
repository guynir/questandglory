plugins {
    java
    antlr
    idea
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.questandglory"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springAiVersion"] = "1.0.0"
//extra["springModulithVersion"] = "1.4.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
//	implementation("org.springframework.ai:spring-ai-starter-model-chat-memory")
//	implementation("org.springframework.ai:spring-ai-starter-model-openai")
//	implementation("org.springframework.modulith:spring-modulith-starter-core")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
//	testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("dev.langchain4j:langchain4j:1.1.0")
    implementation("dev.langchain4j:langchain4j-open-ai:1.1.0")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("ognl:ognl:3.4.7")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    antlr("org.antlr:antlr4:4.13.2")
    implementation("org.antlr:antlr4-runtime:4.13.2")
}

dependencyManagement {
    imports {
//		mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
//		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.generateGrammarSource {
    outputDirectory = file("${project.buildDir}/generated/sources/main/kotlin/antlr")
    arguments = listOf("-package", "com.questandglory.parser.antlr", "-visitor")
}


sourceSets {
    main {
        java {
            srcDir(tasks.generateGrammarSource)
        }
    }
}