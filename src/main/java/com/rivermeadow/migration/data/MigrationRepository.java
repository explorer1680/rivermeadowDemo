package com.rivermeadow.migration.data;

import org.springframework.data.repository.CrudRepository;

import com.rivermeadow.migration.model.Migration;

public interface MigrationRepository extends CrudRepository<Migration, Long>{

}
