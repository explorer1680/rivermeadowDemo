package com.rivermeadow.migration.controller;

import java.util.Optional;

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

import com.rivermeadow.migration.model.TargetCloud;
import com.rivermeadow.migration.service.MigrationService;
import com.rivermeadow.migration.service.exception.TargetCloudNotExistException;

@RestController
@RequestMapping("/targetclouds")
public class TargetCloudController {
	
	static private final String UPDATE_SUCCESS = "update success";
	
	@Autowired
	private MigrationService migrationService;
	
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Long> addTargetCloud(@Valid @RequestBody TargetCloud targetCloud) {
		Long id = migrationService.addTargetCloud(targetCloud);
		return ResponseEntity.status(HttpStatus.CREATED).body(id);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteTargetCloud(@PathVariable("id") Long id){

		if (migrationService.targetCloudExistsById(id)) {
			migrationService.deletetargetCloudById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> updateTargetCloud(@RequestBody TargetCloud targetCloud)
			throws TargetCloudNotExistException {
		Long id = targetCloud.getId();
		Optional<TargetCloud> exist = migrationService.getTargetCloud(id);
		if (exist.isPresent()) {
			migrationService.updateTargetCloud(targetCloud);
			return ResponseEntity.status(HttpStatus.OK).body(UPDATE_SUCCESS);
			
		} else {
			throw new TargetCloudNotExistException(id);
		}
	}

	@GetMapping("{ip}")
	public Optional<TargetCloud> getTargetCloud(@PathVariable("ip") Long id) {
		return migrationService.getTargetCloud(id);
	}
	
	
}
