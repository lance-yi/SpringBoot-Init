package com.lanceyi.small;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lanceyi.small")
public class SmallApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmallApplication.class, args);
	}
}
