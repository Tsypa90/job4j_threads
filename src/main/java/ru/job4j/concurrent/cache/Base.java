package ru.job4j.concurrent.cache;

import java.util.concurrent.atomic.AtomicInteger;

public class Base {
    private final int id;
    private final AtomicInteger version = new AtomicInteger();
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version.set(version);
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version.get();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
