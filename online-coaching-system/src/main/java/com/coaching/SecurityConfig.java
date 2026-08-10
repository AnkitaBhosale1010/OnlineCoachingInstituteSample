package com.coaching;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;
import lombok.RequiredArgsConstructor;

@Configuration	
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	 private final JwtAuthenticationFilter jwtFilter;

	 @Bean
	 public PasswordEncoder passwordEncoder() {

	      return new BCryptPasswordEncoder();
	 }
	 
	 @Bean
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

	        return config.getAuthenticationManager();
	    }
	 
//	 @Bean
//	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	        http
//	            .csrf(csrf -> csrf.disable())
//	            .authorizeHttpRequests(auth -> auth
//	                .requestMatchers("/api/**").permitAll() // Change to role-based access after adding JWT filter
//	                .anyRequest().authenticated()
//	            );
//	        return http.build();
//	    }
	 
	 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		

		http

     .csrf(csrf -> csrf.disable())

     .cors(Customizer.withDefaults())

     .sessionManagement(session ->
             session.sessionCreationPolicy(
                     SessionCreationPolicy.STATELESS))

     .authorizeHttpRequests(auth -> auth
    		 
    		// Public APIs
    		 .requestMatchers(
    		         "/api/auth/**",
    		         "/swagger-ui/**",
    		         "/v3/api-docs/**")
    		 .permitAll()

             // ADMIN only
             .requestMatchers("/api/admin/**")
             .hasRole("ADMIN")

          // TEACHER or ADMIN
             .requestMatchers(
                     "/api/teachers/**",
                     "/api/courses/teacher/**",
                     "/api/materials/upload")
             .hasAnyRole("ADMIN", "TEACHER")

             // STUDENT or ADMIN
             .requestMatchers("/api/students/**",
            		 		  "/api/enrollments/**",
            		 		  "/api/submissions/**",
            		 		  "/api/results/**")
             .hasAnyRole("ADMIN", "STUDENT")
             
             .requestMatchers("/api/batches/**",
                               "/api/assignments/**",
            		          "/api/materials/**",
            		          "/api/messages/**"	)
             .hasAnyRole("ADMIN", "TEACHER", "STUDENT")
             
             .requestMatchers(HttpMethod.POST, "/api/attendance/**")
             .hasAnyRole("ADMIN", "TEACHER")

             .requestMatchers(HttpMethod.GET, "/api/attendance/**")
             .hasAnyRole("ADMIN", "TEACHER", "STUDENT")

             // Login required
             .anyRequest()
             .authenticated())

             .addFilterBefore(
             jwtFilter,
             UsernamePasswordAuthenticationFilter.class);

             return http.build();
       }
  }

