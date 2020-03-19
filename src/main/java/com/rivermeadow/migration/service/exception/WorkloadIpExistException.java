package com.rivermeadow.migration.service.exception;

public class WorkloadIpExistException extends Exception{
	private static final long serialVersionUID = 1L;

	public WorkloadIpExistException(String st){
		super(st);
	}
}
