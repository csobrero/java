package com.mpx.birjan.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.pojomatic.annotations.AutoProperty;

@Entity
@AutoProperty
@Table(name = "users")
public class User extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6877584950579668093L;

	@Column(unique = true)
	private String username;
	
	private String password;
	
	private boolean enabled;
	
	private String name;
	
	private String surname;
	
	private Float commisionRate = 0.2f;
	
	@ManyToOne
	private Agency agency;

	public User() {
	}

	public User(String username, String password, boolean enabled) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Float getCommisionRate() {
		return commisionRate;
	}

	public void setCommisionRate(Float commisionRate) {
		this.commisionRate = commisionRate;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

}
