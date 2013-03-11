package com.mpx.birjan.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.pojomatic.annotations.AutoProperty;

@Entity
@AutoProperty
@Table(name = "GAME")
public class Game extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -8656741444856723949L;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Lottery lottery;

	@Column(nullable = false)
	private int[] numbers;

	public Game() {
	}
	
	public Game(Lottery lottery, int[] numbers) {
		super();
		this.lottery = lottery;
		this.numbers = numbers;
	}

	public int[] getNumbers() {
		return numbers;
	}

	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

}
