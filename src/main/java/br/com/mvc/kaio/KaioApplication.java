package br.com.mvc.kaio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class KaioApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaioApplication.class, args);
	}

}
