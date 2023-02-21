package com.ntg.productserviceonlineshop.repository;

import com.ntg.productserviceonlineshop.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
