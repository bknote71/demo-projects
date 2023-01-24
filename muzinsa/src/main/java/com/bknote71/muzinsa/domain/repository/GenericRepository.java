package com.bknote71.muzinsa.domain.repository;

import java.util.Optional;

public interface GenericRepository<T> {

    default T save(T t) {
        return null;
    }

    default Optional<T> findById(Long id) {
        return null;
    }

    default void deleteById(Long id) {
    }
}
