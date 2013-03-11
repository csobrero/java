package com.mpx.birjan.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
public class PersonDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	private long id;

	@Column
	private String name;

	@Column
	private String surname;

	@Column
	private String movile;
	
	@OneToMany(mappedBy="person")
	private List<WagerDTO> wagers;

	public PersonDTO() {
	};

	public PersonDTO(long id) {
		this.id = id;
	}

	public PersonDTO(long id, String name, String surname, String movile) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.movile = movile;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getMovile() {
		return movile;
	}

	public void setMovile(String movile) {
		this.movile = movile;
	}

}
