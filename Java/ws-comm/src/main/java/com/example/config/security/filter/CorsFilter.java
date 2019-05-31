package com.example.config.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * CORS filters used to allow HTTP OPTIONS methods.
 */
@Component
public class CorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
		FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}
}
