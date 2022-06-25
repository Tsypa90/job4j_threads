package ru.job4j.concurrent.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public final class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        var value =  users.putIfAbsent(user.getId(), user);
        return user.equals(value);
    }

    public synchronized boolean update(User user) {
        var value = users.replace(user.getId(), user);
        return user.equals(value);
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        if (users.get(fromId) == null || users.get(toId) == null) {
            throw new NullPointerException("There are no Users to transfer amount");
        }
        int fromAmount = users.get(fromId).getAmount();
        int toAmount = users.get(toId).getAmount();
        if (fromAmount < amount) {
            throw new IllegalArgumentException("Not enough amount");
        }
        users.get(fromId).setAmount(fromAmount - amount);
        users.get(toId).setAmount(toAmount + amount);
    }
}
