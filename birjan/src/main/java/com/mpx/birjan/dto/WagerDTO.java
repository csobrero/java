package com.mpx.birjan.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "WAGER")
public class WagerDTO implements Serializable{
	
	private static final long serialVersionUID = -2880044092645381368L;

	@Id
	@Column
	private long id;
	
	@Column
	private float amont;
	
	@Column
	private GameDTO game;
	
	@ManyToOne
	@JoinColumn(name="PERSON_ID")
	private PersonDTO person;

}
