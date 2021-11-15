package com.onnoa.shop.demo.upload.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class OptionalTestDto implements Serializable {
    private String name;

    private OcrCustomerOrderAttrDTO dto;
}
