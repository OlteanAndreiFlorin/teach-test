package ro.sci.group2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan
public class TeachAndLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeachAndLearnApplication.class, args);
	}
}
