package com.fanap.structure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching 
public class StructureApplication {

	public static void main(String[] args) {
		SpringApplication.run(StructureApplication.class, args);
	}

}
