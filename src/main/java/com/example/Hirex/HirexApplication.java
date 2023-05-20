package com.example.Hirex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3000")
public class HirexApplication {

	public static void main(String[] args) {
		SpringApplication.run(HirexApplication.class, args);
		System.out.println("helo");

	}

}
