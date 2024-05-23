package ru.rendaxx.lab8client.model;

import java.util.Collection;

public interface CollectionService<T> {
    void load(Collection<T> collection);
    void add(T t);
    void delete(T t);
    void update(T t);
}
