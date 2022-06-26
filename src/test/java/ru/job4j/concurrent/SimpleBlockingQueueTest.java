package ru.job4j.concurrent;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.atomic.AtomicInteger;

public class SimpleBlockingQueueTest {

    @Test
    public void whenThreadPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        AtomicInteger rsl = new AtomicInteger();
        Thread producer = new Thread(() -> queue.offer(5));
        Thread consumer = new Thread(() -> {
            try {
                rsl.set(queue.poll());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(rsl.get(), is(5));
    }
}