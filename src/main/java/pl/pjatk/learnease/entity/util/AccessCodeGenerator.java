package pl.pjatk.learnease.entity.util;

import java.util.UUID;

public class AccessCodeGenerator {

    public static String generateAccessCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

}