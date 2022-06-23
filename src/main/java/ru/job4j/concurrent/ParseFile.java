package ru.job4j.concurrent;

import java.util.function.Predicate;

public final class ParseFile {
    private final Get get;

    public ParseFile(Get getContent) {
        this.get = getContent;
    }

    public synchronized String getContent(Predicate<Character> filter) {
        return get.getContent(filter);
    }
}
