package com.rivermeadow.migration.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;

@Embeddable
public class WorkloadEmbaddable  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ipAddress;
	
	private Credentials credentials;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Volume> volumeSet = new HashSet<>();

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
		return "WorkloadEmbaddable [ipAddress=" + ipAddress + ", credentials=" + credentials + ", volumeSet="
				+ volumeSet + "]";
	}

}
