package com.mpx.birjan.bean;

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

	@ManyToOne(cascade = CascadeType.ALL)
	private Wager wager;

	@NotNull
	private Object[][] data;
	
	private Float prize;

	public Game() {
	}

	public Game(Lottery lottery, Date date, Wager wager, Object[][] data) {
		this(Status.VALID, lottery, date, wager, data);
	}

	public Game(Status status, Lottery lottery, Date date, Wager wager,
			Object[][] data) {
		this.status = status;
		this.lottery = lottery;
		this.date = date;
		this.wager = wager;
		this.data = data;
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

	public Object[][] getData() {
		return data;
	}

	public Date getDate() {
		return date;
	}

	public Float getPrize() {
		return prize;
	}

	public void setPrize(Float paid) {
		this.prize = paid;
	}

}
