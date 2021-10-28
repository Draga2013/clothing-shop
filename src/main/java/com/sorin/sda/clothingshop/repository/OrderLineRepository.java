package com.sorin.sda.clothingshop.repository;

import com.sorin.sda.clothingshop.model.OrderLine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderLineRepository extends CrudRepository<OrderLine, Long> {
    public OrderLine findByAccount_Email(String email);
    public List<OrderLine> findAllByAccount_Id (Long id);
}
