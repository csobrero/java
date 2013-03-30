package com.mpx.birjan.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.pojomatic.annotations.AutoProperty;

import com.mpx.birjan.service.impl.BirjanUtils;

@Entity
@AutoProperty
@Table(name = "GAME")
public class Game extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -8656741444856723949L;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Status status;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Lottery lottery;

	@NotNull
	@Column(unique = true)
	private String hash;

	@OneToOne(cascade = CascadeType.ALL)
	private Wager wager;

	@NotNull
	private Float[] betAmount;

	@NotNull
	private String[] numbers;

	public Game() {
	}

	public Game(Lottery lottery, String numbers[], Date date) {
		this(Status.OPEN, lottery, null, new Float[]{}, numbers, date);
	}

	public Game(Lottery lottery, Wager wager, Float[] betAmount,
			String numbers[]) {
		this(Status.VALID, lottery, wager, betAmount, numbers, null);
	}

	private Game(Status status, Lottery lottery, Wager wager,
			Float[] betAmount, String numbers[], Date date) {
		this.status = status;
		this.lottery = lottery;
		this.wager = wager;
		this.betAmount = betAmount;
		this.numbers = numbers;
		this.created = date;
		this.hash = BirjanUtils.hashFor("XX", wager.getCreated());
	}

	public String getHash() {
		return hash;
	}

	public Float[] getBetAmount() {
		return betAmount;
	}

	public String[] getNumbers() {
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
