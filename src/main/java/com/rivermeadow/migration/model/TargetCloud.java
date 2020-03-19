package com.rivermeadow.migration.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;

@Entity
public class TargetCloud implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private CloudType cloudType;

	@Valid
	@AttributeOverrides({ @AttributeOverride(name = "username", column = @Column(name = "cloud_username")),
			@AttributeOverride(name = "password", column = @Column(name = "cloud_password")),
			@AttributeOverride(name = "domain", column = @Column(name = "cloud_domain")) })
	private Credentials credentials;

	private WorkloadEmbaddable workload;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CloudType getCloudType() {
		return cloudType;
	}

	public void setCloudType(CloudType cloudType) {
		this.cloudType = cloudType;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public WorkloadEmbaddable getWorkload() {
		return workload;
	}

	public void setWorkload(WorkloadEmbaddable workload) {
		this.workload = workload;
	}
	
	

	@Override
	public String toString() {
		return "TargetCloud [id=" + id + ", cloudType=" + cloudType + ", credentials=" + credentials + ", workload="
				+ workload + "]";
	}

	
}
