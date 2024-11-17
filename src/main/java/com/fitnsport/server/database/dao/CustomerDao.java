package com.fitnsport.server.database.dao;

import com.fitnsport.server.database.entity.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

@Repository
public interface CustomerDao extends MongoRepository<Customer, Integer> {
    Optional<Customer> findByCustomerEmail(String emailId);
}
