package com.fitnsport.server.database.dao;

import com.fitnsport.server.database.entity.Cart;
import com.fitnsport.server.database.entity.WishList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListDao extends MongoRepository<WishList, String> {
    Optional<WishList> findByCustomerId(Integer userId);
}
