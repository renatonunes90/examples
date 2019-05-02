package com.example.exception.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.exception.ErrorMessageGenericException;

@ControllerAdvice
public class RegistryExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
		MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status,
		WebRequest request) {

		String userMsg = messageSource.getMessage(ex.getParameterName() + ".invalid", null,
			LocaleContextHolder.getLocale());
		String desenvMsg = ex.getMessage();

		return handleExceptionInternal(ex, new ErrorMsg(userMsg, desenvMsg), headers, status,
			request);
	}

	@ExceptionHandler({ ErrorMessageGenericException.class })
	public ResponseEntity<Object> handleErrorMessageGenericException(
		ErrorMessageGenericException ex) {
		String userMsg = ex.getMessage();
		String desenvMsg = ex.toString();
		List<ErrorMsg> erros = Arrays.asList(new ErrorMsg(userMsg, desenvMsg));
		return ResponseEntity.badRequest().body(erros);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(
		EmptyResultDataAccessException ex, WebRequest request) {
		String userMsg = messageSource.getMessage("resource.notfound", null,
			LocaleContextHolder.getLocale());
		String desevnMsg = ex.toString();
		List<ErrorMsg> erros = Arrays.asList(new ErrorMsg(userMsg, desevnMsg));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	public static class ErrorMsg {

		private String userMessage;

		private String desenvMessage;

		public ErrorMsg(String userMsg , String desenvMsg) {
			this.userMessage = userMsg;
			this.desenvMessage = desenvMsg;
		}

		public String getUserMessage() {
			return userMessage;
		}

		public void setUserMessage(String userMessage) {
			this.userMessage = userMessage;
		}

		public String getDesenvMessage() {
			return desenvMessage;
		}

		public void setDesenvMessage(String desenvMessage) {
			this.desenvMessage = desenvMessage;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("").append(" {");
			sb.append("\"userMessage\": ").append(this.userMessage);
			sb.append("\"desenvMessage\": ").append(this.desenvMessage);
			return sb.append("}").toString();
		}
	}
}
