package com.rivermeadow.migration.controller.advice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rivermeadow.migration.service.exception.MigrationAlreadyRun;
import com.rivermeadow.migration.service.exception.MigrationNotExistException;
import com.rivermeadow.migration.service.exception.TargetCloudNotExistException;
import com.rivermeadow.migration.service.exception.WorkloadIpExistException;
import com.rivermeadow.migration.service.exception.WorkloadNotExistException;
import com.rivermeadow.migration.ui.model.Result;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	static private final String IP_ALREADY_EXIST = "ip address %s already exist in system!";
	static private final String WORKLOAD_NOT_EXIST = "ip address %s not exist in system!";
	static private final String TARGETCLOUD_NOT_EXIST = "target cloud with id: %s not exist in system";
	static private final String MIGRATION_NOT_EXIST = "migration with id: %s not exist in system";
	static private final String MIGRATION_IS_RUNNING = "migration with id: %s is running now";

	@ExceptionHandler(WorkloadIpExistException.class)
	public ResponseEntity<Result> handleConflict(WorkloadIpExistException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new Result(String.format(MyExceptionHandler.IP_ALREADY_EXIST, ex.getMessage())));
	}

	@ExceptionHandler(WorkloadNotExistException.class)
	public ResponseEntity<Result> handleNotExistWorkload(WorkloadNotExistException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Result(String.format(MyExceptionHandler.WORKLOAD_NOT_EXIST, ex.getMessage())));
	}

	@ExceptionHandler(TargetCloudNotExistException.class)
	public ResponseEntity<Result> handleTargetCloudNotExist(TargetCloudNotExistException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Result(String.format(MyExceptionHandler.TARGETCLOUD_NOT_EXIST, ex.getMessage())));
	}

	@ExceptionHandler(MigrationNotExistException.class)
	public ResponseEntity<Result> handleMigrationNotExist(MigrationNotExistException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Result(String.format(MyExceptionHandler.MIGRATION_NOT_EXIST, ex.getMessage())));
	}
	
	@ExceptionHandler(MigrationAlreadyRun.class)
	public ResponseEntity<Result> handleMigrationAlreadyRun(MigrationAlreadyRun ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Result(String.format(MyExceptionHandler.MIGRATION_IS_RUNNING, ex.getMessage())));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
	    List<String> errors = new ArrayList<String>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        errors.add(error.getField() + ": " + error.getDefaultMessage());
	    }
	    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	        errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	    }
	     
	    ApiError apiError = 
	      new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
	    return handleExceptionInternal(
	      ex, apiError, headers, apiError.getStatus(), request);
		
	}
}
