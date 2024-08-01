package com.otechdong.moyeo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MoyeoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoyeoApplication.class, args);
	}

}
