package com.spring.cloud.ms.product.main;

import com.spring.cloud.ms.common.key.SnowFlakeWorker;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.spring.cloud.ms.product")
@MapperScan(basePackages = "com.spring.cloud.ms.product", annotationClass = Mapper.class)
@EnableCaching
public class MsProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProductApplication.class, args);
    }

}
