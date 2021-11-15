package com.onnoa.shop.demo.upload.controller;

import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.common.utils.ByteUtils;
import com.onnoa.shop.demo.upload.queue.KafkaSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping(value = "/kafka")
@Slf4j
public class KafkaController {


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private KafkaSender kafkaSender;


    @PostMapping(value = "/test")
    public ResultBean test() {
        for (int i = 0; i < 1000; i++) {
            kafkaTemplate.send("topic3", "message-" + i);
        }
        return ResultBean.success();

    }

    @PostMapping(value = "/single")
    public ResultBean single() {
        try {
            kafkaSender.sendMessage("testTopic", "kafka test send data");
            return ResultBean.success();
        } catch (Exception e) {
            return ResultBean.error(e.getMessage());
        }

    }

    @PostMapping(value = "/excel")
    public void excel(HttpServletResponse response) throws IOException {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("exceltemplate/dataTempele.csv");
            byte[] bFile = ByteUtils.getBytesFromFile(inputStream);
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("dataTempele.csv", "UTF-8"))));
            response.setHeader("Content-Type", "application/ms-csv;charset=UTF-8");
            response.getOutputStream().write(bFile);
        } catch (IOException e) {
            log.error("excel模板下载失败 :", e);
        }
    }


    public static void main(String[] args) {
        int size = 0;
        try {
            for (int i = -1; i < 10; i++) {
                int j = 10 / i;
                System.out.println(j);
            }
        } catch (Exception e) {
            log.error("",e);
            size++;
        }
    }


}
