package com.fitnsport.server.database.dao;

import com.fitnsport.server.database.entity.Cart;
import com.fitnsport.server.database.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartDao extends MongoRepository<Cart, Integer> {
    Optional<Cart> findByCartUserId(Integer userId);
}
