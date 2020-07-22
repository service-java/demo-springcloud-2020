package com.spring.cloud.gateway.main;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UtcTimeMain {

    public static void main(String[] args) {
        String timeStr1 = ZonedDateTime.now() // 获取当前时间
                // 格式化时间
                .format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        String timeStr2 = ZonedDateTime.now() // 获取当前时间
                // 减少1个小时
                .plusMinutes(2)
                // 格式化时间
                .format(DateTimeFormatter.ISO_ZONED_DATE_TIME);

        System.out.println(timeStr1);
        System.out.println(timeStr2);
    }
}
