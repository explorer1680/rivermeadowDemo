package com.rivermeadow.migration.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

import com.rivermeadow.migration.data.MigrationRepository;
import com.rivermeadow.migration.model.Migration;
import com.rivermeadow.migration.model.MigrationStatus;
import com.rivermeadow.migration.model.TargetCloud;
import com.rivermeadow.migration.model.Volume;
import com.rivermeadow.migration.model.Workload;
import com.rivermeadow.migration.model.WorkloadEmbaddable;
import com.rivermeadow.migration.service.exception.MigrationAlreadyRun;

//@SpringBootTest
////@Disabled
@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class ServiceTest {

	
	@Mock
	private MigrationRepository migrationRepository;
	
	
//	MigrationRepository migrationRepository = mock(MigrationRepository.class);

	@InjectMocks // auto inject helloRepository
	private MigrationService migrationService = new MigrationService();


	@Test
	public void testMigrationSelectedVolumeNoTHaveC() throws MigrationAlreadyRun, InterruptedException {

//		ReflectionTestUtils.setField(migrationService, "migrationRepository", migrationRepository);
		
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

		Migration m = migrationService.runMigration(1L);
		
		assertTrue(m.getMigrationStatus().toString().equals("ERROR"));
	}
	
	@Disabled//it has a sleep inside of program. If you want to run, it will wait 1min to finish
	@Test
	public void testMigrationSelectedVolumeHaveC() throws MigrationAlreadyRun, InterruptedException {

		Migration migrationWittC = new Migration();
		migrationWittC.setId(1L);
		Set<Volume> volumeSet = new HashSet<>();
		Volume v1 = new Volume();
		v1.setMountPoint("C:\\");
		v1.setSize(10L);
		Volume v2 = new Volume();
		v2.setMountPoint("F:\\");
		v2.setSize(20L);
		volumeSet.add(v1);
		volumeSet.add(v2);

		migrationWittC.setSelectedVolume(volumeSet);
		migrationWittC.setMigrationStatus(MigrationStatus.NOTSTARTED);
		
		Set<Volume> volumeSource = new HashSet<>();
		Volume v3 = new Volume();
		v3.setMountPoint("D:\\");
		v3.setSize(10L);
		Volume v4 = new Volume();
		v4.setMountPoint("F:\\");
		v4.setSize(20L);
		volumeSource.add(v3);
		volumeSource.add(v4);
		
		Workload wl = new Workload();
		wl.setVolumeSet(volumeSource);
		
		System.out.println(wl);
		
		migrationWittC.setSource(wl);
		
		WorkloadEmbaddable wl2 = new WorkloadEmbaddable();
		TargetCloud targetCloud = new TargetCloud();
		targetCloud.setWorkload(wl2);
		migrationWittC.setTarget(targetCloud);
		
		System.out.println(migrationWittC);
		
		Optional<Migration> migrationOptional = Optional.of(migrationWittC);

		when(migrationRepository.findById(1L)).thenReturn(migrationOptional);

		Migration m = migrationService.runMigration(1L);
		
		assertTrue(m.getMigrationStatus().toString().equals("SUCCESS"));
	}
}
