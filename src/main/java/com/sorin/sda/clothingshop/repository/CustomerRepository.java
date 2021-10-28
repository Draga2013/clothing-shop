package com.sorin.sda.clothingshop.repository;

import com.sorin.sda.clothingshop.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Product, Long> {
}
