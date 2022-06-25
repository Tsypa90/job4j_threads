package ru.job4j.concurrent.userstorage;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenTransferThenFirstUserHave50() {
        UserStorage userStorage = new UserStorage();
        User one = new User(1, 100);
        User two = new User(2, 200);
        userStorage.add(one);
        userStorage.add(two);
        userStorage.transfer(1, 2, 50);
        assertThat(one.getAmount(), is(50));
    }

    @Test
    public void whenTransferThenSecondUserHave250() {
        UserStorage userStorage = new UserStorage();
        User one = new User(1, 100);
        User two = new User(2, 200);
        userStorage.add(one);
        userStorage.add(two);
        userStorage.transfer(1, 2, 50);
        assertThat(two.getAmount(), is(250));
    }

    @Test
    public void whenTwoThreadsTransferMoney() throws InterruptedException {
        UserStorage store = new UserStorage();
        User first = new User(1, 100);
        User second = new User(2, 200);
        store.add(first);
        store.add(second);
        Thread threadOne = new Thread(() -> {
            store.transfer(1, 2, 50);
        });
        Thread threadTwo = new Thread(() -> {
            store.transfer(1, 2, 50);
        });
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        assertThat(first.getAmount(), is(0));
    }

    @Test (expected = Exception.class)
    public void whenTransferNotEnoughAmountThenException() {
        UserStorage userStorage = new UserStorage();
        User one = new User(1, 100);
        User two = new User(2, 200);
        userStorage.add(one);
        userStorage.add(two);
        userStorage.transfer(1, 2, 120);
    }

    @Test (expected = Exception.class)
    public void whenTransferMoneyToNullUser() {
        UserStorage userStorage = new UserStorage();
        User one = new User(1, 100);
        userStorage.add(one);
        userStorage.transfer(1, 2, 120);
    }


}