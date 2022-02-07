package com.everis.d4i.tutorial.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.everis.d4i.tutorial.security.filters.JwtAuthorizationFilter;
import com.everis.d4i.tutorial.services.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
//			.authorizeRequests()
//			.antMatchers(HttpMethod.POST).hasRole("ADMIN")
//			.antMatchers(HttpMethod.PATCH).hasRole("ADMIN")
//			.antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
//			.antMatchers(HttpMethod.GET).hasRole("USER")
//			.and()
			.httpBasic()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilter(jwtAuthorizationFilter());
		
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception{
		return new JwtAuthorizationFilter(this.authenticationManager());
	}

}
