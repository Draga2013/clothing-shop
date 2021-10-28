package com.sorin.sda.clothingshop.repository;

import com.sorin.sda.clothingshop.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {
    public List<Product>  findAllByCategory_CategoryName (String categoryName);

    public Product findByIdAndSize_Size(Long id, String size);
}
