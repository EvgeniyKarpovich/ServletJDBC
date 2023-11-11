package by.karpovich.service;

import java.util.List;

public interface BaseService<T, K> {

    T findById(K id);

    List<T> findAll();

    boolean deleteById(K id);

    T save(T t);

    void update(T t, K k);
}
