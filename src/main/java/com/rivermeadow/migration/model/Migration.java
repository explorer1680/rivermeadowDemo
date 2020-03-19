package com.rivermeadow.migration.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Migration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ElementCollection
	private Set<Volume> selectedVolume = new HashSet<>();
	@OneToOne
	private Workload source;
	@OneToOne
	private TargetCloud target;
	
	@Enumerated(EnumType.STRING)
	private MigrationStatus migrationStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Volume> getSelectedVolume() {
		return selectedVolume;
	}

	public void setSelectedVolume(Set<Volume> selectedVolume) {
		this.selectedVolume = selectedVolume;
	}

	public Workload getSource() {
		return source;
	}

	public void setSource(Workload source) {
		this.source = source;
	}

	public TargetCloud getTarget() {
		return target;
	}

	public void setTarget(TargetCloud target) {
		this.target = target;
	}

	public MigrationStatus getMigrationStatus() {
		return migrationStatus;
	}

	public void setMigrationStatus(MigrationStatus migrationStatus) {
		this.migrationStatus = migrationStatus;
	}

	@Override
	public String toString() {
		return "Migration [id=" + id + ", selectedVolume=" + selectedVolume + ", source=" + source + ", target="
				+ target + ", migrationStatus=" + migrationStatus + "]";
	}
	
}
