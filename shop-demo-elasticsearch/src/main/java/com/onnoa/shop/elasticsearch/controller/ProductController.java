package com.onnoa.shop.elasticsearch.controller;

import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.elasticsearch.dto.Product;
import com.onnoa.shop.elasticsearch.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    public ResultBean findByProductName(@RequestParam("productName") String productName) {
        List<Product> productList = productRepository.findByProductName(productName);
        return ResultBean.success(productList);
    }
}
