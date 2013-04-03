package com.mpx.birjan.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.pojomatic.annotations.AutoProperty;

@Entity
@AutoProperty
@Table(name = "DRAW", uniqueConstraints = @UniqueConstraint(columnNames={"lottery","date"}))
public class Draw extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -8987502749907941006L;

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
	private String[] numbers;

	public Draw() {
	}
	
	public Draw(Lottery lottery, Date date, String[] numbers) {
		this.status = Status.OPEN;
		this.lottery = lottery;
		this.date = date;
		this.numbers = numbers;
	}

	public Status getStatus() {
		return status;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public Date getDate() {
		return date;
	}

	public String[] getNumbers() {
		return numbers;
	}

}