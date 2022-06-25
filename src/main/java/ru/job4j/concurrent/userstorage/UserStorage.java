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
        return users.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        var userFrom = users.get(fromId);
        var userTo = users.get(toId);
        if (userFrom == null || userTo == null) {
            throw new NullPointerException("There are no Users to transfer amount");
        }
        int fromAmount = userFrom.getAmount();
        int toAmount = userTo.getAmount();
        if (fromAmount < amount) {
            throw new IllegalArgumentException("Not enough amount");
        }
        userFrom.setAmount(fromAmount - amount);
        userTo.setAmount(toAmount + amount);
    }
}
