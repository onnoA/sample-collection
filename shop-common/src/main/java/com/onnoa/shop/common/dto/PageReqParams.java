package com.onnoa.shop.common.dto;

import javax.validation.constraints.NotNull;

/**
 * 分页请求参数
 */
public class PageReqParams {
    private static final int DEFAULT_NO = 1;

    private static final int DEFAULT_SIZE = 20;

    private Integer pageNo = 1;

    private Integer pageSize = 20;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
