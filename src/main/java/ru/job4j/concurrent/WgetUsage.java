package ru.job4j.concurrent;

import java.util.HashMap;
import java.util.Map;

public class WgetUsage {
    private static Map<String, String> argsValues = new HashMap<>();
    private static String URL = "url";
    private static String SPEED = "speed";

    private static void validateArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("There are no args!");
        }
        for (String str : args) {
            if (!str.contains("=") && !str.startsWith("-")) {
                throw new IllegalArgumentException("Args not correct!");
            }
            String key = str.substring(str.indexOf("-") + 1, str.indexOf("="));
            String value = str.substring(str.indexOf('=') + 1);
            if (key.isEmpty() || value.isEmpty()) {
                throw new IllegalArgumentException("Key or Value is empty.");
            }
            argsValues.put(key, value);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validateArgs(args);
        String url = argsValues.get(URL);
        int speed = Integer.parseInt(argsValues.get(SPEED));
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
