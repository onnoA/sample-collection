package com.onnoa.shop.demo.upload.service;


import com.onnoa.shop.demo.upload.dto.OcsRmInterfaceResponse;

import java.util.List;

public interface AnZhenTongApiService {

    OcsRmInterfaceResponse<List<String>> getImageList(String custOrderId);
}
