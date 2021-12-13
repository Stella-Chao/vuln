package com.tf.backend;

import com.spring4all.mongodb.EnableMongoPlus;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.tf.backend2.dao")
@SpringBootApplication
@EnableMongoPlus
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
