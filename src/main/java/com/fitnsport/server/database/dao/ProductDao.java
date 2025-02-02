package com.fitnsport.server.database.dao;

import com.fitnsport.server.Enums.ProductTypeEnum;
import com.fitnsport.server.database.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductDao extends MongoRepository<Product, String> {

    long countByProductTypeEnum(ProductTypeEnum productTypeEnum);

    default List<Product> findTopRatedProducts() {
        return findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "rating"))).getContent();
    }
}
