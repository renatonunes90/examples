package com.example.common.responseadvices;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

/**
 * Class responsible for intercept every request and adapt defafult configurations.
 */
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

	private static final Logger logger = LoggerFactory.getLogger("applogger");

	@Override
	public boolean supports(MethodParameter returnType,
		Class<? extends HttpMessageConverter<?>> converterType) {

		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType,
		MediaType selectedContentType,
		Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
		ServerHttpResponse response) {

		setResponseStatusByResponseData(body, response);

		int status = HttpStatus.OK.value();
		if (response instanceof ServletServerHttpResponse) {
			status = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
		}

		writeLog(body, request, status);

		return body;
	}

	private void setResponseStatusByResponseData(Object object, ServerHttpResponse response) {
		setResponseStatus2xx(object, response);
	}

	private void setResponseStatus2xx(Object object, ServerHttpResponse response) {
		if (Objects.isNull(object)) {
			response.setStatusCode(HttpStatus.NO_CONTENT);
		} else if (object instanceof Collection<?>) {
			Collection<?> collection = (Collection<?>) object;

			if (collection.isEmpty()) {
				response.setStatusCode(HttpStatus.NO_CONTENT);
			}
		}
	}

	private void writeLog(Object object, ServerHttpRequest request, int status) {

		HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

		String host = request.getURI().getHost();
		HttpMethod method = request.getMethod();

		String path = request.getURI().getPath();
		if (path.equals("/ws-comm/error")) {
			path = getRealPathOfErrorObj(object);
		}

		String operator = "";

		// read payload
		String postData = "";
		try {
			postData = readPayload(servletRequest);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String queryParams = request.getURI().getQuery();

		logger.info("Requested from: " + host);
		logger.info("Requested Method: " + method);
		logger.info("Requested resource: " + path);
		logger.info("Requested operator: " + operator);
		logger.info("Requested query params: " + queryParams);
		logger.info("Requested body data: " + postData);
		logger.info("Response status: " + status);
		logger.info("Response body data: " + object);
	}

	private String getRealPathOfErrorObj(Object object) {

		if (object instanceof LinkedHashMap) {
			String path = (String) ((LinkedHashMap<?, ?>) object).get("path");
			if (path != null) {
				return path;
			}
		}

		return "";
	}

	private String readPayload(final HttpServletRequest request) throws IOException {
		String payloadData = null;
		ContentCachingRequestWrapper contentCachingRequestWrapper = WebUtils.getNativeRequest(
			request, ContentCachingRequestWrapper.class);
		if (null != contentCachingRequestWrapper) {
			byte[] buf = contentCachingRequestWrapper.getContentAsByteArray();
			if (buf.length > 0) {
				payloadData = new String(buf, 0, buf.length,
					contentCachingRequestWrapper.getCharacterEncoding());
			}
		}
		return payloadData;
	}
}
