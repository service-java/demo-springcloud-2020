package com.spring.cloud.sso.server.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**** imports ****/
@SpringBootApplication(scanBasePackages="com.spring.cloud.sso.server")
public class SsoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoServerApplication.class, args);
	}

}
