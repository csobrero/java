package com.mpx.birjan.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.pojomatic.annotations.AutoProperty;

import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.common.Status;

@Entity
@AutoProperty
@Table(name = "DRAW", uniqueConstraints = @UniqueConstraint(columnNames = {
		"lottery", "date" }))
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
	@ManyToOne
	private User user;

	@NotNull
	private String[] numbers;

	public Draw() {
	}

	public Draw(Lottery lottery, Date date, User user, String[] numbers) {
		this.status = Status.OPEN;
		this.lottery = lottery;
		this.date = date;
		this.user = user;
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

	public final void setNumbers(String[] numbers) {
		this.numbers = numbers;
	}

	public final void setStatus(Status status) {
		this.status = status;
	}

	public final User getUser() {
		return user;
	}

}
