package ru.job4j.concurrent;

import java.util.function.Predicate;

public interface Get {
    public String getContent(Predicate<Character> filter);
}
