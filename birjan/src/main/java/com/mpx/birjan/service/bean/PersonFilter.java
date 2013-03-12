package com.mpx.birjan.service.bean;

import java.io.Serializable;

import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class PersonFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String surname;

	private String movile;

	public PersonFilter() {
	}

	public PersonFilter(Long id, String name, String surname, String movile) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.movile = movile;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getSurname() {
		return surname;
	}

	public final void setSurname(String surname) {
		this.surname = surname;
	}

	public final String getMovile() {
		return movile;
	}

	public final void setMovile(String movile) {
		this.movile = movile;
	}

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
