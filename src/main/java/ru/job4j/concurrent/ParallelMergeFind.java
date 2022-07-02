package ru.job4j.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelMergeFind extends RecursiveTask<Integer> {
    private final Object[] array;
    private final Object elementToSearch;
    private final int from;
    private final int to;
    private static final int OPTIMISTIC = 10;

    private ParallelMergeFind(Object[] array, Object element, int from, int to) {
        this.array = array;
        this.elementToSearch = element;
        this.from = from;
        this.to = to;
    }

    @Override
    protected synchronized Integer compute() {
        int rsl = -1;
        if (array.length <= OPTIMISTIC) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(elementToSearch)) {
                    rsl = i;
                    return rsl;
                }
            }
        }
        if (to >= from) {
            int mid = (from + to) / 2;
            ParallelMergeFind leftFind = new ParallelMergeFind(array, elementToSearch, from, mid - 1);
            ParallelMergeFind rightFind = new ParallelMergeFind(array, elementToSearch, mid + 1, to);
            leftFind.fork();
            rightFind.fork();
            int left = leftFind.join();
            int right = rightFind.join();
            if (array[mid].equals(elementToSearch)) {
                rsl = mid;
                return rsl;
            }
            if (left != -1) {
                rsl = left;
            } else if (right != -1){
                rsl = right;
            }
        }
        return rsl;
    }

    public static int find(Object[] array, Object element) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelMergeFind(array, element, 0, array.length - 1));
    }
}
