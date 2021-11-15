package com.onnoa.shop.common.pattern.factory.demo6lazyinitial;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProductFactory {

    private static final Map<String, Product> map = new HashMap<>();

    public static synchronized Product createProduct(String type) {
        Product product = null;
        if (map.containsKey(type)) {
            product = map.get(type);
        } else {
            ProductTypeEnum productTypeEnum = ProductTypeEnum.getByCode(type);
            if (Objects.nonNull(productTypeEnum)) {
                product = productTypeEnum.getProductType();
                map.put(productTypeEnum.getCode(), product);
            }
        }
        return product;
    }
}
