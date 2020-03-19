package com.rivermeadow.migration.service.exception;

public class WorkloadNotExistException  extends Exception{

	private static final long serialVersionUID = 1L;

	public WorkloadNotExistException(String st){
		super(st);
	}
}
