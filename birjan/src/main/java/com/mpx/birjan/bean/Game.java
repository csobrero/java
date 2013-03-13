package com.mpx.birjan.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

	@NotNull
	@Enumerated(EnumType.STRING)
	private Lottery lottery;

	@NotNull
	private Integer[] numbers;

	public Game() {
	}
	
	public Game(Lottery lottery, Integer[] numbers) {
		this.status = Status.VALID;
		this.lottery = lottery;
		this.numbers = numbers;
	}

	public Integer[] getNumbers() {
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

}
