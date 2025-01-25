package com.fitnsport.server.database.dao;

import com.fitnsport.server.database.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends MongoRepository<Product, String> {
}
