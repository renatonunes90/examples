package com.example.config.security;

/**
 * Configuration class for HTTP resources/requests.
 */
//@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResourceConfig {}
/*extends ResourceServerConfigurerAdapter {

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

		http.cors().configurationSource(source).and().authorizeRequests().antMatchers(
			HttpMethod.OPTIONS, "/**").permitAll();
		//.antMatchers("/login").permitAll().anyRequest().authenticated().and().formLogin().permitAll();
	}

}*/
