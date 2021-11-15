package com.onnoa.shop.demo.upload.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum TypeEnum {

    OCS_LIVING_AUTH("OCS_LIVING_AUTH", "图片稽核", "图片稽核"),
    CUSTOMER_ORDER_ATTR("CUSTOMER_ORDER_ATTR", "pdf切图", "pdf切图"),
    OCR_RM_INTER("OCR_RM_INTER", "端口占用", "端口占用"),

    ;

    // 类型
    private String type;
    // 详情
    private String msg;
    // 备注
    private String remark;

    public static TypeEnum getByType(String type) {
        Optional<TypeEnum> optional = Stream.of(TypeEnum.values())
                .filter(typeEnum -> typeEnum.type.equals(type))
                .findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

}
