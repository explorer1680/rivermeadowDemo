package com.rivermeadow.migration.service;



import com.rivermeadow.migration.data.MigrationRepository;
import com.rivermeadow.migration.model.Migration;
import com.rivermeadow.migration.model.MigrationStatus;
import com.rivermeadow.migration.model.Volume;
import com.rivermeadow.migration.service.exception.MigrationAlreadyRun;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceTest {

	@Mock
	private MigrationRepository migrationRepository;

	@InjectMocks // auto inject helloRepository
	private MigrationService MigrationService = new MigrationService();;

	@BeforeEach
	void setMockOutput() {
		Migration migrationWithoutC = new Migration();
		migrationWithoutC.setId(1L);
		Set<Volume> volumeSet = new HashSet<>();
		Volume v1 = new Volume();
		v1.setMountPoint("D:\\");
		v1.setSize(10L);
		Volume v2 = new Volume();
		v2.setMountPoint("F:\\");
		v2.setSize(20L);
		volumeSet.add(v1);
		volumeSet.add(v2);

		migrationWithoutC.setSelectedVolume(volumeSet);
		
		Optional<Migration> migrationOptional = Optional.of(migrationWithoutC);

		when(migrationRepository.findById(1L)).thenReturn(migrationOptional);
	}

	@DisplayName("Test Mock helloService + helloRepository")
	@Test
	public void testMigrationSelectedVolumeNoHaveC() throws MigrationAlreadyRun, InterruptedException {

		MigrationService MigrationService = new MigrationService();
		
//		MigrationRepository migrationRepository = mock(MigrationRepository.class);
		
		Migration migrationWithoutC = new Migration();
		migrationWithoutC.setId(1L);
		Set<Volume> volumeSet = new HashSet<>();
		Volume v1 = new Volume();
		v1.setMountPoint("D:\\");
		v1.setSize(10L);
		Volume v2 = new Volume();
		v2.setMountPoint("F:\\");
		v2.setSize(20L);
		volumeSet.add(v1);
		volumeSet.add(v2);
		

		migrationWithoutC.setSelectedVolume(volumeSet);
		migrationWithoutC.setMigrationStatus(MigrationStatus.NOTSTARTED);
		
		Optional<Migration> migrationOptional = Optional.of(migrationWithoutC);
		
		when(migrationRepository.findById(1L)).thenReturn(migrationOptional);
		
		MigrationService.setMigrationRepository(migrationRepository);
		//
		// Migration migrationWithoutC = new Migration();
		// migrationWithoutC.setId(1L);
		// Set<Volume> volumeSet = new HashSet<>();
		// Volume v1= new Volume();
		// v1.setMountPoint("D:\\");
		// v1.setSize(10L);
		// Volume v2= new Volume();
		// v2.setMountPoint("F:\\");
		// v2.setSize(20L);
		// volumeSet.add(v1);
		// volumeSet.add(v2);
		//
		//
		// migrationWithoutC.setSelectedVolume(volumeSet);
		//
		// PowerMockito.when(migrationRepository.findById(1L).get()).thenReturn(migrationWithoutC);

		Migration m = MigrationService.runMigration(1L);
		
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println(m.getMigrationStatus());
		assertTrue(m.getMigrationStatus().toString().equals("ERROR"));
		// WorkloadRepository mock =PowerMockito.mock(WorkloadRepository.class);
		// MigrationService
	}
}
