package com.coaching;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration	
@EnableWebSecurity
public class SecurityConfig {
	

	 @Bean
	 public PasswordEncoder passwordEncoder() {

	      return new BCryptPasswordEncoder();
	 }
	 
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/**").permitAll() // Change to role-based access after adding JWT filter
	                .anyRequest().authenticated()
	            );
	        return http.build();
	    }
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		
//
//		http.csrf(csrf -> csrf.disable())
//        .authorizeHttpRequests(auth ->auth
//        		
//                        .requestMatchers("/api/auth/**").permitAll()
//
//                        .requestMatchers("/api/admin/**")
//                        .hasAnyRole("ADMIN")
//
//                        .requestMatchers("/api/teachers/**")
//                        .hasAnyRole("ADMIN","TEACHER")
//
//                        .requestMatchers("/api/students/**")
//                        .hasAnyRole("ADMIN","STUDENT")
//
//                        .anyRequest()
//                        .authenticated()
//        );
//
//        return http.build();
//    }
}
