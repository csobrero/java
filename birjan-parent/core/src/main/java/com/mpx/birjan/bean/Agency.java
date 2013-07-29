package com.mpx.birjan.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

@Entity
@AutoProperty
@Table(name = "agency")
public class Agency extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 5623626971531831540L;

	@OneToOne
	private User principal;

	private String name;

	private boolean enabled;

	@NotNull
	private String email;

	@Property(policy = PojomaticPolicy.NONE)
	@OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
	private List<User> users;

	private Float commisionRate;

	public Agency() {
	}

	public User getPrincipal() {
		return principal;
	}

	public void setPrincipal(User principal) {
		this.principal = principal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Float getCommisionRate() {
		return commisionRate;
	}

	public void setCommisionRate(Float commisionRate) {
		this.commisionRate = commisionRate;
	}

}
