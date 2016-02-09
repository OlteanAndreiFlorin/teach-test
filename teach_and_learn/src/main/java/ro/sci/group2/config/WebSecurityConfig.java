package ro.sci.group2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import ro.sci.group2.service.UserDetailsServiceImp;

/**
 * Web security configuration implementation is responsible for authorizing and
 * authenticating users
 * 
 * @author Oltean Andrei
 *
 */
@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	// @formatter:off
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.csrf().disable()
			.headers().disable()
			.authorizeRequests()
				.antMatchers("/teacher/**").hasRole("TEACHER")
				.antMatchers("/","/home").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/user").authenticated()
				.and()
			.formLogin().and()
			.httpBasic();
		}
		// @formatter:on
	// @formatter:off
		@Autowired
		public void configGlobal(AuthenticationManagerBuilder auth,
				UserDetailsServiceImp userDetailsService) throws Exception {
			auth
			.userDetailsService(userDetailsService).and()
			.inMemoryAuthentication()
				.withUser("admin")
					.password("admin")
					.roles("ADMIN")
				.and()
				.withUser("user")
					.password("user")
					.roles("STUDENT");
		}
		// @formatter:on
}
