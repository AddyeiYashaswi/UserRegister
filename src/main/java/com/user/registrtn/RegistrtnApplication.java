package com.user.registrtn;

import com.user.registrtn.exception.CustomizedResponseEntityExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class RegistrtnApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrtnApplication.class, args);
	}

}
