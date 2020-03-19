package com.rivermeadow.migration.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rivermeadow.migration.data.MigrationRepository;
import com.rivermeadow.migration.data.TargetCloudRepository;
import com.rivermeadow.migration.data.WorkloadRepository;
import com.rivermeadow.migration.model.Migration;
import com.rivermeadow.migration.model.MigrationStatus;
import com.rivermeadow.migration.model.TargetCloud;
import com.rivermeadow.migration.model.Volume;
import com.rivermeadow.migration.model.Workload;
import com.rivermeadow.migration.model.WorkloadEmbaddable;
import com.rivermeadow.migration.service.exception.MigrationAlreadyRun;
import com.rivermeadow.migration.service.exception.TargetCloudNotExistException;
import com.rivermeadow.migration.service.exception.WorkloadNotExistException;

@Service
public class MigrationService {

	@Autowired
	private WorkloadRepository workloadRepository;

	@Autowired
	private TargetCloudRepository targetCloudRepository;

	@Autowired
	private MigrationRepository migrationRepository;

	public void setMigrationRepository(MigrationRepository migrationRepository) {
		this.migrationRepository = migrationRepository;
	}

	public void addWorkload(Workload workload) {
		workloadRepository.save(workload);
	}
	
	public void updateWorkload(Workload workload) {
		workloadRepository.save(workload);
	}

	public Workload getWorkload(String ip) {
		return workloadRepository.findByIpAddress(ip);
	}

	public void deleteByIpAddress(String ip) {
		workloadRepository.deleteById(ip);
	}

	public boolean existsByIpAddress(String ip) {
		return workloadRepository.existsByIpAddress(ip);
	}

	public Long addTargetCloud(TargetCloud targetCloud) {
		return targetCloudRepository.save(targetCloud).getId();
	}
	
	public void updateTargetCloud(TargetCloud targetCloud){
		targetCloudRepository.save(targetCloud);
	}

	public Optional<TargetCloud> getTargetCloud(Long id) {
		return targetCloudRepository.findById(id);
	}

	public boolean targetCloudExistsById(Long id) {
		return targetCloudRepository.existsById(id);
	}

	public void deletetargetCloudById(Long id) {
		targetCloudRepository.deleteById(id);
	}

	public Long addMigration(Migration migration) throws TargetCloudNotExistException, WorkloadNotExistException {
		Workload workload = workloadRepository.findByIpAddress(migration.getSource().getIpAddress());
		if(workload == null){
			throw new WorkloadNotExistException(migration.getSource().getIpAddress());
		}
		
		Optional<TargetCloud> targetCloud = targetCloudRepository.findById(migration.getTarget().getId());
		if(!targetCloud.isPresent()){
			throw new TargetCloudNotExistException(migration.getTarget().getId());
		}
		migration.setSource(workload);
		migration.setTarget(targetCloud.get());
		return migrationRepository.save(migration).getId();
	}
	
	public void updateMigration(Migration migration){
		migrationRepository.save(migration);
	}

	public Optional<Migration> getMigration(Long id) {
		return migrationRepository.findById(id);
	}

	public boolean migrationExistsById(Long id) {
		return migrationRepository.existsById(id);
	}

	public void deleteMigrationById(Long id) {
		migrationRepository.deleteById(id);
	}

	public Migration runMigration(Long id) throws MigrationAlreadyRun, InterruptedException {
		Migration migration = migrationRepository.findById(id).get();
		if(migration.getMigrationStatus().equals(MigrationStatus.RUNNING)){//it is running now
			throw new MigrationAlreadyRun(id);
		}
		migration.setMigrationStatus(MigrationStatus.RUNNING);
		migrationRepository.save(migration);
		
		
		
		Set<Volume> selectedVolume = new HashSet<>(migration.getSelectedVolume());
		List<String> slectedMountPointList = new ArrayList<>();
		selectedVolume.stream().forEach(p -> slectedMountPointList.add(p.getMountPoint()));
		
		if (selectedVolume.stream().filter(p -> p.getMountPoint().equalsIgnoreCase("C:\\")).collect(Collectors.toList())
				.size() > 0) {
			
			Thread.sleep(60000);//setup 1 min
			
			Set<Volume> volumeSet = new HashSet<>();
			migration.getSource().getVolumeSet().stream().filter(p -> slectedMountPointList.contains(p.getMountPoint())).forEach(p -> volumeSet.add(p));
			
			WorkloadEmbaddable workload = new WorkloadEmbaddable();
			workload.setVolumeSet(new HashSet<>(volumeSet));
			
			migration.getTarget().getWorkload().setVolumeSet(volumeSet);;

			migration.setMigrationStatus(MigrationStatus.SUCCESS);
		} else {
			migration.setMigrationStatus(MigrationStatus.ERROR);
		}
		updateMigration(migration);
		return migration;
	}
}
