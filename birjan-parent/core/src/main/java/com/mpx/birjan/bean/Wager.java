package com.mpx.birjan.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import com.mpx.birjan.util.BirjanUtils;

@Entity
@AutoProperty
@Table(name = "WAGER")
public class Wager extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 4452318623121262333L;

	@NotNull
	@Column(unique = true)
	private String hash;

	@NotNull
	private float betAmount;

	private Float winAmount;

	@Property(policy = PojomaticPolicy.NONE)
	@OneToMany(mappedBy = "wager", cascade = CascadeType.ALL)
	private List<Game> games;

	// @PrimaryKeyJoinColumn
	@ManyToOne(cascade = CascadeType.ALL)
	private Person person;
	
	@NotNull
	@ManyToOne
	private User user;

	public Wager() {
	}

	public Wager(float betAmount, User user) {
		this(betAmount, user, null);
	}

	public Wager(float betAmount, User user, Person person) {
		this.betAmount = betAmount;
		this.user = user;
		this.person = person;
		this.hash = BirjanUtils.hashFor(this.getUser().getUsername(), this.getCreated());
	}

	public float getBetAmount() {
		return betAmount;
	}

	public Float getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(float winAmount) {
		this.winAmount = winAmount;
	}

	public List<Game> getGame() {
		return games;
	}

	public void setGame(List<Game> games) {
		this.games = games;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public final User getUser() {
		return user;
	}

	public String getHash() {
		return hash;
	}

}
