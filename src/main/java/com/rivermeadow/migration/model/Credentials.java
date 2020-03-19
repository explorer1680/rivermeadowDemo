package com.rivermeadow.migration.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Credentials implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "username can not be null")
	private String username;
	@NotNull(message = "password can not be null")
	private String password;
	private String domain;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return "Credentials [username=" + username + ", password=" + password + ", domain=" + domain + "]";
	}

}
