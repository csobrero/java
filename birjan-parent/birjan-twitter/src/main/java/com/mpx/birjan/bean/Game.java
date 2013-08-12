package com.mpx.birjan.bean;

import static com.mpx.birjan.common.Status.LOSER;
import static com.mpx.birjan.common.Status.WINNER;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.pojomatic.annotations.AutoProperty;

import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.common.Status;

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
	@Temporal(TemporalType.DATE)
	private Date date;

	@NotNull
	private String number;

	private Integer position;

	@NotNull
	private Float amount;

	private Float prize;

	@ManyToOne(cascade = CascadeType.ALL)
	private Wager wager;

	public Game() {
	}
	
	public Game(Lottery lottery, Date date, Wager wager, Object[][] data) {
		this(lottery, date, (String)data[0][0], (Integer)data[0][1], (Float)data[0][2], wager);
		
	}

	public Game(Lottery lottery, Date date, String number, Integer position, Float amount, Wager wager) {
		this.status = Status.VALID;
		this.lottery = lottery;
		this.date = date;
		this.number = number;
		this.position = position;
		this.amount = amount;
		this.wager = wager;
	}

	public boolean is(Status state) {
		return this.status.equals(state);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public Date getDate() {
		return date;
	}

	public String getNumber() {
		return number;
	}

	public Integer getPosition() {
		return position;
	}

	public Float getAmount() {
		return amount;
	}

	public Float getPrize() {
		return prize;
	}

	public void setPrize(Float prize) {
		this.prize = prize; 
		this.status = prize!=null&&prize>0?WINNER:LOSER;
	}

	public Wager getWager() {
		return wager;
	}

	public Object[][] getData() {
		Object[][] data = new Object[1][1];
		data[0] = new Object[]{getNumber(),getPosition(),getAmount()};
		return data;
	}

}
