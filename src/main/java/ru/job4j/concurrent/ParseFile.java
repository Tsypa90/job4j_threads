package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;
    private final Get get;

    public ParseFile(File file, Get getContent) {
        this.file = file;
        this.get = getContent;
    }

    public synchronized String getContent(Predicate<Character> filter) {
        return get.getContent(file);
    }
}
