package com.rivermeadow.migration.service.exception;

public class TargetCloudNotExistException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public TargetCloudNotExistException(Long id){
		super(id.toString());
	}

}
