package com.springboot.springmongooauthjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
public class SpringMongoOauthJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMongoOauthJwtApplication.class, args);
	}

}
