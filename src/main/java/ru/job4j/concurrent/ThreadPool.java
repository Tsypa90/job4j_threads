package ru.job4j.concurrent;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);
    private boolean running = true;

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                try {
                    while (running) {
                        var task = tasks.poll();
                        if (task != null) {
                            task.run();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }));
            threads.get(i).start();
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        if (running) {
            tasks.offer(job);
        }
    }

    public void shutdown() {
        running = false;
    }
}
