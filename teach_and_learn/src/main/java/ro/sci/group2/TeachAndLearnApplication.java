package ro.sci.group2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ro.sci.group2.dao.UserDAO;
import ro.sci.group2.dao.inmemory.IMUserDAO;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class TeachAndLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeachAndLearnApplication.class, args);
	}

	@Bean
	public UserDAO userDao() {
		return // new JDBCUserDao("", "", "", "", "");

		new IMUserDAO();

	}
}
