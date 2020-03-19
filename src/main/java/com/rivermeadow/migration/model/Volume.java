package com.rivermeadow.migration.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Volume implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mountPoint;
	private Long size;

	public Volume() {
	}

	public Volume(String mountPoint, Long size) {
		super();
		this.mountPoint = mountPoint;
		this.size = size;
	}

	public String getMountPoint() {
		return mountPoint;
	}

	public void setMountPoint(String mountPoint) {
		this.mountPoint = mountPoint;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Volume [mountPoint=" + mountPoint + ", size=" + size + "]";
	}

}
