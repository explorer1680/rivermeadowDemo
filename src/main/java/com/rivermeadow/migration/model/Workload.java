package com.rivermeadow.migration.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Workload implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id // can't changed
	@NotNull(message = "ip address can not be null")
	private String ipAddress;
	@Valid
	private Credentials credentials;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Volume> volumeSet = new HashSet<>();

	public Workload() {
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Set<Volume> getVolumeSet() {
		return volumeSet;
	}

	public void setVolumeSet(Set<Volume> volumeSet) {
		this.volumeSet = volumeSet;
	}

	@Override
	public String toString() {
		return "Workload [ipAddress=" + ipAddress + ", credentials=" + credentials + ", volumeSet=" + volumeSet + "]";
	}

}
