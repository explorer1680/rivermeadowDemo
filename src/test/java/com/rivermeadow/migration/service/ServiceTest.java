package com.rivermeadow.migration.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.rivermeadow.migration.data.MigrationRepository;
import com.rivermeadow.migration.model.Migration;
import com.rivermeadow.migration.model.MigrationStatus;
import com.rivermeadow.migration.model.Volume;
import com.rivermeadow.migration.service.exception.MigrationAlreadyRun;

@SpringBootTest
@Disabled
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

		Migration m = MigrationService.runMigration(1L);
		
		assertTrue(m.getMigrationStatus().toString().equals("ERROR"));
	}
}
