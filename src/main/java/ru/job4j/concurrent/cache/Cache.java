package ru.job4j.concurrent.cache;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (id, base) -> {
            if (base.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
                    return model;
                }
        ) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}
