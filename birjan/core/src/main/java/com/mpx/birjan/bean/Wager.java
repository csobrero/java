package com.mpx.birjan.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

@Entity
@AutoProperty
@Table(name = "WAGER")
public class Wager extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 4452318623121262333L;

	@NotNull
	private float betAmount;

	private Float winAmount;

	@Property(policy = PojomaticPolicy.NONE)
	@OneToOne(mappedBy = "wager", cascade = CascadeType.ALL)
	private Game game;

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

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
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

}
