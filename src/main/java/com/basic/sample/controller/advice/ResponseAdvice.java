package com.basic.sample.controller.advice;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.basic.sample.contract.exception.EntityNotFound;
import com.basic.sample.contract.response.ApiResponse;
import com.basic.sample.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ResponseAdvice {
	
	@Autowired
    private IMessageService messageService;

	@ExceptionHandler(EntityNotFound.class)
	public ResponseEntity<ApiResponse<?>> entityNotFound(EntityNotFound ex) {
		return new ResponseEntity<>(ApiResponse.<String>builder()
				.errors(Arrays.asList(ex.getMessage()))
				.hasError(true)
				.build(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		return new ResponseEntity<>(ApiResponse.builder()
				.hasError(true)
				.errors(errors)
				.build(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public void methodArgumentTypeMismatchException(final HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ApiResponse<String>> responseStatusException(final ResponseStatusException ex) throws IOException {
		return new ResponseEntity<>(ApiResponse.<String>builder()
				.errors(Arrays.asList(messageService.getMessage(ex.getStatus().name(), ex.getReason())))
				.hasError(true)
				.build(), ex.getStatus());
	}
}