package ru.job4j.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelMergeFind<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T elementToSearch;
    private final int from;
    private final int to;
    private static final int OPTIMISTIC = 10;

    public ParallelMergeFind(T[] array, T element, int from, int to) {
        this.array = array;
        this.elementToSearch = element;
        this.from = from;
        this.to = to;
    }

    @Override
    protected synchronized Integer compute() {
        int rsl = -1;
        if ((to - from) <= OPTIMISTIC) {
            for (int i = from; i <= to; i++) {
                if (array[i] == elementToSearch) {
                    rsl = i;
                    return rsl;
                }
            }
        } else {
            int mid = (from + to) / 2;
            System.out.println(Thread.currentThread().getName());
            ParallelMergeFind<T> leftFind = new ParallelMergeFind<>(array, elementToSearch, from, mid);
            ParallelMergeFind<T> rightFind = new ParallelMergeFind<>(array, elementToSearch, mid + 1, to);
            leftFind.fork();
            rightFind.fork();
            int left = leftFind.join();
            int right = rightFind.join();
            return Math.max(left, right);
        }
        return rsl;
    }

   public static<T> int find(T[] array, T element) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelMergeFind<>(array, element, 0, array.length - 1));
    }
}
