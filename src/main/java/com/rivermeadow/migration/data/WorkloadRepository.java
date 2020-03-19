package com.rivermeadow.migration.data;

import org.springframework.data.repository.CrudRepository;

import com.rivermeadow.migration.model.Workload;

public interface WorkloadRepository extends CrudRepository<Workload, String> {

	Workload findByIpAddress(String ip);

	boolean existsByIpAddress(String ip);

}
