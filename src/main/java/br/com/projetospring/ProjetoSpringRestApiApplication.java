package br.com.projetospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.projetospring.models"})
@ComponentScan(basePackages = {"br.*"})
@EnableJpaRepositories(basePackages = {"br.com.projetospring.repositories"})
@EnableTransactionManagement
@RestController
@EnableWebSecurity
@EnableAutoConfiguration
public class ProjetoSpringRestApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjetoSpringRestApiApplication.class, args);
	}

}
