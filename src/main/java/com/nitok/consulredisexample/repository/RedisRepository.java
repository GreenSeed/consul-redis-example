package com.nitok.consulredisexample.repository;

import com.nitok.consulredisexample.model.Product;

import java.util.Map;

public interface RedisRepository {
    Map<Object, Object> findAllProducts();

    void add(Product product);

    void deleteAllProducts();

    void deleteById(Long id);

    Product findProductById(Long id);
}
