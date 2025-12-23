package com.example.zensoproductservice.repository;

import com.example.zensoproductservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

    Product findByProductName(String productName);
    List<Product> findByCompanyName(String companyName);
}
