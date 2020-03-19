package com.rivermeadow.migration.service.exception;

public class MigrationNotExistException extends Exception{
	private static final long serialVersionUID = 1L;
	public MigrationNotExistException(Long id){
		super(id.toString());
	}
}
