package com.ey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * @author George.Rosario
 *
 */
@SpringBootApplication
public class RegistrationAndLoginJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationAndLoginJwtApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	      return new BCryptPasswordEncoder();
	}
}
