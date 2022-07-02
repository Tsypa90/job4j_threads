package ru.job4j.concurrent;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParallelMergeFindTest {

    @Test
    public void whenArrayLengthLess10() {
        String[] array = new String[]{"pavel", "olga", "petr", "andrey", "oleg"};
        assertThat(1, is(ParallelMergeFind.find(array, "olga")));
    }

    @Test
    public void whenArrayLength1() {
        String[] array = new String[]{"pavel"};
        assertThat(0, is(ParallelMergeFind.find(array, "pavel")));
    }

    @Test
    public void whenArrayNoElementToFind() {
        String[] array = new String[]{"pavel", "olga", "petr", "andrey", "oleg"};
        assertThat(-1, is(ParallelMergeFind.find(array, "dima")));
    }

    @Test
    public void whenArrayLengthMoreThen10() {
        String[] array = new String[]{"pavel", "olga", "petr", "andrey", "oleg", "dima", "ilya", "platon", "fedor", "evgeniy", "yana"};
        assertThat(7, is(ParallelMergeFind.find(array, "platon")));
    }

    @Test
    public void whenArrayLengthMoreThen10NoElementToFind() {
        String[] array = new String[]{"pavel", "olga", "petr", "andrey", "oleg", "dima", "ilya", "platon", "fedor", "evgeniy", "yana"};
        assertThat(-1, is(ParallelMergeFind.find(array, "platonishe")));
    }
}