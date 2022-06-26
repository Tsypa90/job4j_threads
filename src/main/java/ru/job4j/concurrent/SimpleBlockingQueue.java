package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int bound;

    public SimpleBlockingQueue(int bound) {
        this.bound = bound;
    }

    public synchronized void offer(T value) {
        if (queue.size() < bound) {
            queue.add(value);
            this.notifyAll();
        }
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        T rsl = queue.poll();
        this.notifyAll();
        return rsl;
    }
}
