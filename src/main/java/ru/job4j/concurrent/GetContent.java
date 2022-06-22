package ru.job4j.concurrent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class GetContent implements Get {

    @Override
    public String getContent(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines().forEach(content::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
