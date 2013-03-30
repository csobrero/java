package com.mpx.birjan.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

//@Entity
@AutoProperty
@Table(name = "User")
public class User extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 7474097657451254479L;

	private String name;

	private String surname;

	@Property(policy = PojomaticPolicy.NONE)
	@OneToMany(mappedBy = "user")
	private List<Wager> wagers;

	public User() {
	}

	public User(Long id, String name, String surname) {
		if (id != null)
			this.id = id;
		this.name = name;
		this.surname = surname;
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

	public List<Wager> getWagers() {
		return wagers;
	}

	public void setWagers(List<Wager> wagers) {
		this.wagers = wagers;
	}

}
