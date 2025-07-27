package com.questandglory.utils;

public class GlobalIdGenerator {

    public static final IdGenerator<String> idGenerator = new SecureRandomIdGenerator();

    public static String generateId() {
        return idGenerator.generate();
    }
}
