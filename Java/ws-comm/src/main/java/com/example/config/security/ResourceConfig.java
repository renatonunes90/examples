package com.example.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResourceConfig extends ResourceServerConfigurerAdapter {

   @Value("${app.cors.granted.urls}")
   private String[] grantedOrigins;

   @Override
   public void configure(HttpSecurity http) throws Exception {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);

      Arrays.stream(grantedOrigins).forEach(origin -> {
         config.addAllowedOrigin(origin.trim());
      });

      config.addAllowedHeader("Authorization");
      config.addAllowedHeader("Content-Type");
      config.addAllowedHeader("Accept");
      config.addAllowedHeader("Origin");

      config.addAllowedMethod("POST");
      config.addAllowedMethod("GET");
      config.addAllowedMethod("DELETE");
      config.addAllowedMethod("PUT");
      config.addAllowedMethod("OPTIONS");

      source.registerCorsConfiguration("/**", config);

      http.cors()
         .configurationSource(source)
         .and()
         .authorizeRequests()
         .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
         .antMatchers("/**").permitAll()
         .anyRequest().authenticated()
         .and()
         .formLogin().permitAll();
   }

}
