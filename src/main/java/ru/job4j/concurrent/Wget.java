package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private static final int SECOND = 1000;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var name = url.split("/");
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(name[name.length - 1])) {
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            int downloadData = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, speed)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long pause = System.currentTimeMillis() - start;
                    if (pause < SECOND) {
                        try {
                            Thread.sleep(SECOND - pause);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    downloadData = 0;
                    start = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
