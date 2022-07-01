package ru.job4j.concurrent.executorServiceEmail;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ThreadSafe
public class EmailNotification {
    @GuardedBy("this")
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public synchronized void emailTo(User user) {
        pool.submit(() -> {
            String subject = "Notification " + user.getName() + " to email " + user.getEmail();
            String body = "Add a new event to " + user.getName();
            send(subject, body, user.getEmail());
            System.out.println("Email send!" + Thread.currentThread().getName());
        });
    }

    public synchronized void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {
    }
}
