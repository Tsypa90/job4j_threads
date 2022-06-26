package ru.job4j.concurrent;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SimpleBlockingQueueTest {

    @Test
    public void whenThreadPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        AtomicInteger rsl = new AtomicInteger();
        Thread producer = new Thread(() -> {
            try {
                queue.offer(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(i -> {
                    try {
                        queue.offer(i);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
            }
        ));
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}