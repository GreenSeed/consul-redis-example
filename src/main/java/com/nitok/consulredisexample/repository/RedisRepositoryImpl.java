package com.nitok.consulredisexample.repository;

import com.nitok.consulredisexample.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private static final String KEY = "Product";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, Object, Object> hashOperations;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<Object, Object> findAllProducts() {
        return hashOperations.entries(KEY);
    }

    @Override
    public void deleteAllProducts() {
        hashOperations.delete(KEY);
    }

    @Override
    public void add(Product product) {
        hashOperations.put(KEY, product.getId(), product.getName());
    }

    @Override
    public void deleteById(String id) {
        hashOperations.delete(KEY, id);
    }

    @Override
    public Product findProductById(String id) {
        return (Product) hashOperations.get(KEY, id);
    }
}
