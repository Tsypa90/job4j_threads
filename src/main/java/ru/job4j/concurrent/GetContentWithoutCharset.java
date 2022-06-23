package ru.job4j.concurrent;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

public final class GetContentWithoutCharset implements Get {
    private final File file;

    public GetContentWithoutCharset(File file) {
        this.file = file;
    }

    @Override
    public String getContent(Predicate<Character> filter) {
        String content = "";
        try (FileReader reader = new FileReader(file)) {
            int data;
            if ((data = reader.read()) != -1) {
                if (filter.test((char) data)) {
                    content += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
