    package com.onnoa.shop.common.pattern.factory.demo6lazyinitial;


import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum ProductTypeEnum {

    PRO_ONE("1", new ConcreteProduct1()),
    PRO_TWO("2", new ConcreteProduct2()),
    ;

    private String code;

    private Product productType;

    ProductTypeEnum(String code, Product productType) {
        this.code = code;
        this.productType = productType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Product getProductType() {
        return productType;
    }

    public void setProductType(Product productType) {
        this.productType = productType;
    }

    public static ProductTypeEnum getByCode(String code) {
        Optional<Stream<ProductTypeEnum>> enumStream = Optional.ofNullable(Arrays.stream(ProductTypeEnum.values()).filter(pro -> pro.code.equals(code)));
        if (enumStream.isPresent()) {
            return enumStream.get().findFirst().get();
        }
        return null;
    }

}
