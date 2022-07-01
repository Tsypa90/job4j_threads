package ru.job4j.concurrent;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        Runnable task = null;
                        try {
                            task = tasks.poll();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        if (task != null) {
                            task.run();
                        }
                    }});
            threads.add(thread);
            thread.start();
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}
