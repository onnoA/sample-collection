package com.onnoa.shop.demo.video.thread;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class DealProcessSream extends Thread{
    private InputStream inputStream;

    public DealProcessSream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    @Override
    public void run() {
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        try {
            inputStreamReader = new InputStreamReader(
                    inputStream);
            br = new BufferedReader(inputStreamReader);
            // 打印信息
            String line = null;
            while ((line = br.readLine()) != null) {
               log.debug(line);
            }
            // 不打印信息
//            while (br.readLine() != null);
        } catch (IOException ioe) {
            log.error("Exception:{}", ioe);
        }finally {
            try {
                br.close();
                inputStreamReader.close();
            } catch (IOException e) {
                log.error("Exception:{}", e);
            }
        }

    }
}
