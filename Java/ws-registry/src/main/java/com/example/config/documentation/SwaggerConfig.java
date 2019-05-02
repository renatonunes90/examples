package com.example.config.documentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration class.
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

	private static final String API_TITLE = "WS Registry";

	private static final String API_DESCRIPTION = "Web service Registry.";

	@Autowired
	private Environment environment;

	/**
	 * Cria um Docket bean para injeção de dependência.
	 * 
	 * @return docket
	 */
	@Bean
	public Docket docketBean() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(
			RequestHandlerSelectors.basePackage("br.pucrs.controllers")).paths(PathSelectors.regex(
				"/.*")).build().apiInfo(buildApiInfo()).useDefaultResponseMessages(
					false).globalResponseMessage(RequestMethod.GET, List.of(
						new ResponseMessageBuilder().code(200).message(environment.getProperty(
							"ws.swagger.response.status.200.message")).build(),
						new ResponseMessageBuilder().code(201).message(environment.getProperty(
							"ws.swagger.response.status.201.message")).build(),
						new ResponseMessageBuilder().code(204).message(environment.getProperty(
							"ws.swagger.response.status.204.message")).build(),
						new ResponseMessageBuilder().code(400).message(environment.getProperty(
							"ws.swagger.response.status.400.message")).build(),
						new ResponseMessageBuilder().code(404).message(environment.getProperty(
							"ws.swagger.response.status.404.message")).build()));
	}

	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder().title(API_TITLE).description(API_DESCRIPTION).build();
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations(
			"classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations(
			"classpath:/META-INF/resources/webjars/");
	}

}
