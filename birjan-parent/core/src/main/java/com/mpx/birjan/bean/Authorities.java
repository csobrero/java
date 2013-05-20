package com.mpx.birjan.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.pojomatic.annotations.AutoProperty;

@Entity
@AutoProperty
@Table(name = "authorities")
public class Authorities extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 4637423801972058123L;
	
	private String username;
	
	private String authority;

	public Authorities() {
	}

	public Authorities(String username, String authority) {
		this.username = username;
		this.authority = authority;
	}

	public final String getUsername() {
		return username;
	}

	public final String getAuthority() {
		return authority;
	}

}
