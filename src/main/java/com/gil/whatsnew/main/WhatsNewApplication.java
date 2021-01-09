package com.gil.whatsnew.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@ComponentScan("com.gil.whatsnew")
public class WhatsNewApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatsNewApplication.class, args);
	}

}
