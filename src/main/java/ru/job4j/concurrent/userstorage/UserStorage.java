package ru.job4j.concurrent.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public final class UserStorage {
    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (!users.contains(user)) {
            rsl = true;
            users.put(user.getId(), user);
        }
        return rsl;
    }

    public synchronized boolean update(User user) {
        return add(user);
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        int fromAmount = users.get(fromId).getAmount();
        int toAmount = users.get(toId).getAmount();
        users.get(fromId).setAmount(fromAmount - amount);
        users.get(toId).setAmount(toAmount + amount);
    }
}
