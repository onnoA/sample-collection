package com.onnoa.shop.elasticsearch.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Data
@Builder
@Document(indexName = "product",type = "shop")
public class Product implements Serializable {

    private String productName;

    private String desc;


}
