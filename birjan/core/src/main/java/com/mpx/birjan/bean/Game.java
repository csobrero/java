package com.mpx.birjan.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.pojomatic.annotations.AutoProperty;

import com.mpx.birjan.service.impl.BirjanUtils;

@Entity
@AutoProperty
@Table(name = "GAME")
public class Game extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -8656741444856723949L;

	@NotNull
	@Column(unique = true)
	private String hash;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Status status;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Lottery lottery;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date date;

	@OneToOne(cascade = CascadeType.ALL)
	private Wager wager;

	@Lob
	@NotNull
	private byte[] data;

	public Game() {
	}

	public Game(Lottery lottery, Date date, Wager wager, byte[] data) {
		this(Status.VALID, lottery, date, wager, data);
	}

	public Game(Status status, Lottery lottery, Date date, Wager wager,
			byte[] data) {
		this.status = status;
		this.lottery = lottery;
		this.date = date;
		this.wager = wager;
		this.data = data;
		this.hash = BirjanUtils.hashFor("XX", (wager!=null)?wager.getCreated():new Date());
	}

	public String getHash() {
		return hash;
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

	public byte[] getData() {
		return data;
	}

	public Date getDate() {
		return date;
	}

}
