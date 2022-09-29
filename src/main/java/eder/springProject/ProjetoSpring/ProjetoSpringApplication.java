package eder.springProject.ProjetoSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//pra ser que é spring
@SpringBootApplication
@EntityScan(basePackages = {"eder.springProject.ProjetoSpring.model"})
@ComponentScan(basePackages = {"eder.*"})
//qual a pasta que vai ficar as interface
@EnableJpaRepositories(basePackages = {"eder.springProject.ProjetoSpring.repository"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
public class ProjetoSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoSpringApplication.class, args);
	}

}
