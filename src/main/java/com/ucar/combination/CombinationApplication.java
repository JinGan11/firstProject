package com.ucar.combination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CombinationApplication {

	public static void main(String[] args) {

		SpringApplication.run(CombinationApplication.class, args);
	}

}
