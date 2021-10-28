package com.sorin.sda.clothingshop.service;

import com.sorin.sda.clothingshop.model.Category;
import com.sorin.sda.clothingshop.model.Product;
import com.sorin.sda.clothingshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<Category> getAll(){
        return new ArrayList<Category>((Collection<? extends Category>) categoryRepository.findAll());
    }
}
