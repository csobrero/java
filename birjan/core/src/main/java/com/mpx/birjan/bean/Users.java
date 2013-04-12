package com.mpx.birjan.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.pojomatic.annotations.AutoProperty;

@Entity
@AutoProperty
@Table(name = "users")
public class Users extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6877584950579668093L;

	@Column(unique = true)
	private String username;
	
	private String password;
	
	private boolean enabled;

	public Users() {
	}

	public Users(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public final String getUsername() {
		return username;
	}

	public final void setUsername(String username) {
		this.username = username;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final boolean getEnabled() {
		return enabled;
	}

	public final void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
