package com.kiramijyan.dynamicbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan
@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class DynamicBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicBatchApplication.class, args);
	}

}
