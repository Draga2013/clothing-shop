package com.sorin.sda.clothingshop.service;

import com.sorin.sda.clothingshop.model.Product;
import com.sorin.sda.clothingshop.repository.ProductRepository;

import com.sorin.sda.clothingshop.service.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    public List<Product> getAll(){
        return new ArrayList<Product>((Collection<? extends Product>) productRepository.findAll());
    }

    public ProductDTO findProductDTObyId(Long productDTOId){
        Product product = productRepository.findById(productDTOId).orElse(null);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPictureUrl(product.getPictureUrl());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory());
        return productDTO;
    }

    public List<Product> findProductsByCategoryName(String categoryName){
        List<Product> products = productRepository.findAllByCategory_CategoryName(categoryName);
        return products;
    }
    public void save(ProductDTO productDTO){
        Product product= new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPictureUrl(productDTO.getPictureUrl());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productDTO.getCategory());
        productRepository.save(product);
    }
    public void update(ProductDTO productDTO){
        Product product = productRepository.findById(productDTO.getId()).orElse(null);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPictureUrl(productDTO.getPictureUrl());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productDTO.getCategory());
        productRepository.save(product);
    }

    public void delete(Long id){
        Product product = productRepository.findById(id).orElse(null);
        productRepository.delete(product);
    }

}
