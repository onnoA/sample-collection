package com.onnoa.shop.common.thread;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月17日 19:41
 */
public class Client {

    public static void main(String[] args) {
        Map<String, Object> tmpMap = new HashMap<>(6);
        for (int i = 0; i < 10; i++) {
            tmpMap.put(String.valueOf(i),i+1);
        }
        System.out.println(JSON.toJSON(tmpMap));
//        ThreadTest threadTest = new ThreadTest();
//        Thread thread = new Thread(threadTest);
//        thread.start();

        int toNextDaySeconds = getToNextDaySeconds();
        System.out.println(toNextDaySeconds);

        String addr = sortNameSrvAddr("172.16.84.183:9001;172.16.84.187:9001");
        System.out.println(addr);
    }

    private static String sortNameSrvAddr(String nameSrvAddr) {

        if (StringUtils.isEmpty(nameSrvAddr)) {
            return nameSrvAddr;
        }
        String[] namesSrvArr = nameSrvAddr.split("[;,]");
        if (namesSrvArr.length > 1) {
            List<String> nameList = Arrays.asList(namesSrvArr);
            Collections.sort(nameList);
            return StringUtils.join(nameList, ";");
        }
        return nameSrvAddr;
    }

    public static int getToNextDaySeconds() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 当前时间毫秒数
        Long startLong = calendar.getTime().getTime();
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        // 明天凌晨0点0时0秒毫秒数
        Long endLong = calendar.getTime().getTime();
        Long res = (endLong - startLong) / 1000;
        return res.intValue();
    }
}
