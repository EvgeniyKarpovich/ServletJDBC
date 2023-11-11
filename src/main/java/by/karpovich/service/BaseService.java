package by.karpovich.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T,K> {

    Optional<T> findById(K id);

    List<T> findAll();

    boolean deleteById(K id);

    T save(T t);

    void update(T t);
}
