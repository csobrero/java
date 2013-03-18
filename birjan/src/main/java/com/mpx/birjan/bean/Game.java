package com.mpx.birjan.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.pojomatic.annotations.AutoProperty;

@Entity
@AutoProperty
@Table(name = "GAME")
public class Game extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -8656741444856723949L;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToOne(cascade = CascadeType.ALL)
	private Wager wager;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Lottery lottery;

	@NotNull
	private String numbers;

	public Game(Lottery lottery, String numbers, Date date) {
		this.status = Status.OPEN;
		this.lottery = lottery;
		this.numbers = numbers;
		this.created = date;
	}

	public Game(Lottery lottery, Wager wager, String numbers) {
		this.status = Status.VALID;
		this.lottery = lottery;
		this.numbers = numbers;
		this.wager = wager;
	}

	public String getNumbers() {
		return numbers;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public final Status getStatus() {
		return status;
	}

	public final void setStatus(Status status) {
		this.status = status;
	}

	public Wager getWager() {
		return wager;
	}

	public void setWager(Wager wager) {
		this.wager = wager;
	}

}
