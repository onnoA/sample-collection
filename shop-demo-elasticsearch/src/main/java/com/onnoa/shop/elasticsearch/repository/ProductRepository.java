package com.onnoa.shop.elasticsearch.repository;

import com.onnoa.shop.elasticsearch.dto.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    List<Product> findByProductName(String productName);
}
