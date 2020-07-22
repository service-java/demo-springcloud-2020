package com.spring.cloud.ms.dashboard.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableHystrixDashboard
// 驱动Turbine
@EnableTurbine
public class MsDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsDashboardApplication.class, args);
    }

}
