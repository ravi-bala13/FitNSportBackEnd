package com.fitnsport.server.database.dao;

import com.fitnsport.server.database.entity.Cart;
import com.fitnsport.server.database.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDao extends MongoRepository<Cart, String> {
    Optional<Cart> findByCustomerId(Integer userId);
}
