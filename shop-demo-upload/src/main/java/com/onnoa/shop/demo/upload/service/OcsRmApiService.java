package com.onnoa.shop.demo.upload.service;


import com.onnoa.shop.demo.upload.dto.OcsRmInterfaceResponse;

public interface OcsRmApiService {
    OcsRmInterfaceResponse qryBusPortInfo(String orderId);

    OcsRmInterfaceResponse qryCodeBarPortInfo(String obdScanCode);
}
