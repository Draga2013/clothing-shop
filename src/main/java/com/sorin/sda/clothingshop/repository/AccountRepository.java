package com.sorin.sda.clothingshop.repository;

import com.sorin.sda.clothingshop.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByEmail(String email);
}
