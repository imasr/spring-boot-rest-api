package com.springrest.springrest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springrest.springrest.services.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override()
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable().authorizeRequests()
				// .antMatchers(HttpMethod.GET, "/", SecurityConstants.SIGN_UP_URL +
				// "/**").permitAll()
				.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_IN__URL, SecurityConstants.SIGN_UP_URL + "/**",
						"/upload-file")
				.permitAll()
				.antMatchers(HttpMethod.PUT, SecurityConstants.SIGN_UP_URL + "/**").permitAll()
				.antMatchers(HttpMethod.DELETE, SecurityConstants.SIGN_UP_URL + "/**").permitAll()
				.anyRequest().authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override()
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}
}

// without WebSecurityConfigurerAdapter
// @EnableWebSecurity
// public class WebSecurity {
// private final UserService userService;
// private final BCryptPasswordEncoder bCryptPasswordEncoder;

// public WebSecurity(UserService userService, BCryptPasswordEncoder
// bCryptPasswordEncoder) {
// this.userService = userService;
// this.bCryptPasswordEncoder = bCryptPasswordEncoder;
// }

// @Bean
// protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

// http.csrf().disable().authorizeRequests()
// .antMatchers(HttpMethod.GET, "/", SecurityConstants.SIGN_UP_URL,
// SecurityConstants.SIGN_UP_URL + "/{id}")
// .permitAll()
// .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL,
// SecurityConstants.SIGN_UP_URL + "/bulk",
// "/upload-file")
// .permitAll()
// .antMatchers(HttpMethod.PUT, SecurityConstants.SIGN_UP_URL +
// "/{id}").permitAll()
// .antMatchers(HttpMethod.DELETE, SecurityConstants.SIGN_UP_URL +
// "/{id}").permitAll()
// .anyRequest().authenticated();

// AuthenticationManagerBuilder authenticationManagerBuilder = http
// .getSharedObject(AuthenticationManagerBuilder.class);
// authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
// return http.build();

// }

// }