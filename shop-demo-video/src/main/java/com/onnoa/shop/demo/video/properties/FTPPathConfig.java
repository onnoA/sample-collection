package com.onnoa.shop.demo.video.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class FTPPathConfig {
    //ftp保存文件的路径前缀
    @Value("${ftp.ftpPrefixPath}")
    private String ftpPrefixPath ;

    //本地文件存储路径前缀
    @Value("${ftp.localPrefixPath}")
    private String localPrefixPath;

    //是否直接访问ftp服务器
    private int readLocal =1;

    //设置间隔时间 单位是秒
    @Value("${ftp.interval}")
    private Long interval =0L;
}
