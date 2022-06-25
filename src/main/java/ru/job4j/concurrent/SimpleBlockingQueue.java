package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) {
        queue.add(value);
        this.notifyAll();
    }

    public synchronized T poll() {
        try {
            while (queue.isEmpty()) {
                this.wait();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return queue.poll();
    }
}
