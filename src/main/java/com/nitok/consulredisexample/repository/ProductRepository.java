package com.nitok.consulredisexample.repository;

import com.nitok.consulredisexample.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
