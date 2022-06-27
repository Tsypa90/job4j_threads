package ru.job4j.concurrent;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenIncrement() throws InterruptedException {
        CASCount count = new CASCount();
        Thread one = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                count.increment();
            }
        });
        Thread two = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                count.increment();
            }
        });
        one.start();
        two.start();
        one.join();
        two.join();
        assertThat(count.get(), is(10));
    }

}