package com.example.springpostgresqlcompose.utils;

import org.springframework.stereotype.Service;

@Service
public class StringFormattingUtils {

    public String formatString(String originalString) {
        String[] splitted = originalString.split("\\s+");

        for (int i = 0; i < splitted.length; i++) {
            splitted[i] = Character.toUpperCase(splitted[i].charAt(0)) + splitted[i].substring(1).toLowerCase();
        }

        return String.join(" ", splitted);
    }
}
