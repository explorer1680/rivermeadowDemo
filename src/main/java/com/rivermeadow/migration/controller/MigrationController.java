package com.rivermeadow.migration.controller;

import java.util.Optional;

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

import com.rivermeadow.migration.model.Migration;
import com.rivermeadow.migration.model.MigrationStatus;
import com.rivermeadow.migration.service.MigrationService;
import com.rivermeadow.migration.service.exception.MigrationAlreadyRun;
import com.rivermeadow.migration.service.exception.MigrationNotExistException;
import com.rivermeadow.migration.service.exception.TargetCloudNotExistException;
import com.rivermeadow.migration.service.exception.WorkloadNotExistException;

@RestController
@RequestMapping("/migrations")
public class MigrationController {

	static private final String UPDATE_SUCCESS = "update success";
	static private final String MIGRATION_RUNNING = "migration running";

	@Autowired
	private MigrationService migrationService;

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Long> addMigration(@RequestBody Migration migration) throws TargetCloudNotExistException, WorkloadNotExistException {
		
		migration.setMigrationStatus(MigrationStatus.NOTSTARTED);
		Long id = migrationService.addMigration(migration);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(id);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteMigration(@PathVariable("id") Long id) {

		if (migrationService.migrationExistsById(id)) {
			migrationService.deleteMigrationById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> updateMigration(@RequestBody Migration migration)
			throws MigrationNotExistException {
		Long id = migration.getId();
		Optional<Migration> exist = migrationService.getMigration(id);
		if (exist.isPresent()) {
			migrationService.updateMigration(migration);
			return ResponseEntity.status(HttpStatus.OK).body(UPDATE_SUCCESS);

		} else {
			throw new MigrationNotExistException(id);
		}
	}

	@GetMapping("{ip}")
	public Optional<Migration> getMigration(@PathVariable("ip") Long id) {
		return migrationService.getMigration(id);
	}
	
	@PutMapping("{ip}/run")
	public ResponseEntity<String> runMigration(@PathVariable("ip") Long id) throws MigrationAlreadyRun, MigrationNotExistException, InterruptedException {
		Optional<Migration> exist = migrationService.getMigration(id);
		if (exist.isPresent()) {
			migrationService.runMigration(id);
			return ResponseEntity.status(HttpStatus.OK).body(MIGRATION_RUNNING);

		} else {
			throw new MigrationNotExistException(id);
		}
	}
	
	@GetMapping("{ip}/status")
	public ResponseEntity<String> getMigrationStatus(@PathVariable("ip") Long id) throws MigrationNotExistException {
		Optional<Migration> migration = migrationService.getMigration(id);
		if(migration.isPresent()){
			return ResponseEntity.status(HttpStatus.OK).body(migration.get().getMigrationStatus().toString());
		}else{
			throw new MigrationNotExistException(id);
		}
	}
}
