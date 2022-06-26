package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();
    private static final int START = 0;

    public void increment() {
        AtomicInteger ref;
        do {
            if (count.get() == null) {
                count.compareAndSet(null, START);
            }
            ref = new AtomicInteger(count.get());
        } while (!count.compareAndSet(ref.get(), ref.incrementAndGet()));
    }

    public int get() {
        var value = count.get();
        if (value == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return value;
    }
}
