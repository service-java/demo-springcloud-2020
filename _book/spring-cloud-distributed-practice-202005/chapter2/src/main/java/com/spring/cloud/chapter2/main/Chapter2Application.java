package com.spring.cloud.chapter2.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication(scanBasePackages = "com.spring.cloud.chapter2.*")
// 标识控制器
@Controller
// 请求前缀
@RequestMapping("/chapter2")
public class Chapter2Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter2Application.class, args);
    }

    // HTTP GET请求，且定义REST风格路径和参数
    @GetMapping("/index/{value}")
    public ModelAndView index(ModelAndView mav,
                              @PathVariable("value") String value) {
        // 设置数据模型
        mav.getModelMap().addAttribute("key", value);
        // 请求名称，定位到Thymeleaf模板
        mav.setViewName("index");
        // 返回ModelAndView
        return mav;
    }
}
