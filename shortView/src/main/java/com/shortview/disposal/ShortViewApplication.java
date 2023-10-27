package com.shortview.disposal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.shortview.disposal.configuration")
public class ShortViewApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShortViewApplication.class, args);
	}

}
