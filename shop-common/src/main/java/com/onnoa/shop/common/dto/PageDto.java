package com.onnoa.shop.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageDto<T>  implements Serializable {

    private long total;
    private List<T> records;

}
