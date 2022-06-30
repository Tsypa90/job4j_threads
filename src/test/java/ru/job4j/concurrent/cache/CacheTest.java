package ru.job4j.concurrent.cache;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddAndGetTrue() throws InterruptedException {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        AtomicBoolean rsl = new AtomicBoolean(false);
        Thread one = new Thread(() -> rsl.set(cache.add(base)));
        one.start();
        one.join();
        assertTrue(rsl.get());
    }

    @Test
    public void whenAddTwiceAndGetFalse() throws InterruptedException {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        assertFalse(cache.add(base));
    }

    @Test
    public void whenAddBaseThenChangeNameAndUpdateThenTrue() throws InterruptedException {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("New name");
        AtomicBoolean rsl = new AtomicBoolean(false);
        Thread one = new Thread(() -> cache.add(base));
        Thread two = new Thread(() -> rsl.set(cache.update(base)));
        one.start();
        two.start();
        one.join();
        two.join();
        assertTrue(rsl.get());
    }

    @Test
    public void whenAddThenDeleteThenAddAgainAndTrue() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        cache.delete(base);
        assertTrue(cache.add(base));
    }

    @Test (expected = Exception.class)
    public void whenAddThenDeleteThenAddAgainAndFalse() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base user = new Base(1, 1);
        cache.add(base);
        cache.update(user);
    }

}