package com.rivermeadow.migration.service.exception;

public class MigrationAlreadyRun extends Exception{
	private static final long serialVersionUID = 1L;
	public MigrationAlreadyRun(Long id){
		super(id.toString());
	}
}
