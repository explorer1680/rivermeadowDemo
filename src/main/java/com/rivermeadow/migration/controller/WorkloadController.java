package com.rivermeadow.migration.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rivermeadow.migration.model.Workload;
import com.rivermeadow.migration.service.MigrationService;
import com.rivermeadow.migration.service.exception.WorkloadIpExistException;
import com.rivermeadow.migration.service.exception.WorkloadNotExistException;

@RestController
@RequestMapping("/workloads")
public class WorkloadController {

	static private final String UPDATE_SUCCESS = "update success";

	@Autowired
	private MigrationService migrationService;

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> addWorkload(@Valid @RequestBody Workload workload) throws WorkloadIpExistException {
		String ip = workload.getIpAddress();
		Workload existingWorkload = migrationService.getWorkload(ip);
		if (existingWorkload == null) {
			migrationService.addWorkload(workload);
			return ResponseEntity.status(HttpStatus.CREATED).body(ip);
		} else {
			throw new WorkloadIpExistException(ip);
		}
	}

	@DeleteMapping("{ip}")
	public ResponseEntity<String> deleteWorkload(@PathVariable("ip") String ip){

		if (migrationService.existsByIpAddress(ip)) {
			migrationService.deleteByIpAddress(ip);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> updateWorkload(@Valid @RequestBody Workload workload)
			throws WorkloadNotExistException {
		String ip = workload.getIpAddress();
		Workload existingWorkload = migrationService.getWorkload(ip);
		if (existingWorkload == null) {
			throw new WorkloadNotExistException(ip);
		} else {
			migrationService.updateWorkload(workload);
			return ResponseEntity.status(HttpStatus.OK).body(UPDATE_SUCCESS);
		}
	}

	@GetMapping("{ip}")
	public Workload getWorkload(@PathVariable("ip") String ip) {
		return migrationService.getWorkload(ip);
	}

}
