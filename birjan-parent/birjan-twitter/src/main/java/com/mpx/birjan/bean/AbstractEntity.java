package com.mpx.birjan.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

@AutoProperty
@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Property(policy = PojomaticPolicy.EQUALS_TO_STRING)
	protected Long id;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date created;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	@PrePersist
	protected void onCreate() {
		if (this.created == null){
			this.created = new DateTime().toDate();
			this.createdDate = this.created;
		}
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

}
