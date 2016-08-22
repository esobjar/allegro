package com.allegro.exception;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AllegroControllerAdvice {

	@ResponseBody
	@ExceptionHandler(BindException.class)
	ResponseEntity<VndErrors> bindExceptionHandler(BindException ex) {
		return new ResponseEntity<VndErrors>(new VndErrors("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ResponseBody
	@ExceptionHandler(SearchHistoryNotFound.class)
	ResponseEntity<VndErrors> searchHistoryNotFoundExceptionHandler(SearchHistoryNotFound ex) {
		return new ResponseEntity<VndErrors>(new VndErrors("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ResponseBody
	@ExceptionHandler(SearchLimitExceededException.class)
	ResponseEntity<VndErrors> searchLimitExceededExceptionHandler(SearchLimitExceededException ex) {
		return new ResponseEntity<VndErrors>(new VndErrors("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

}
