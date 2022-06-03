package com.csv.employeeskillstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class EmployeeskillstrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeskillstrackerApplication.class, args);
	}

}
