package ru.job4j.concurrent;

import java.io.*;

public final class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)))) {
            printWriter.write(content);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
