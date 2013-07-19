package com.mpx.birjan.service.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Path;


public class Filter<T> {

	private String fieldName;

	private T term;

	private boolean equal;
	
	private boolean not;
	
	private List<String> fetch;

	public Filter(String fieldName, T term) {
		this(fieldName, term, true, false);
	}

	public Filter(String fieldName, T term, boolean not) {
		this(fieldName, term, true, not);
	}

	public Filter(String fieldName, T term, boolean equal, boolean not) {
		this.fieldName = fieldName;
		this.term = term;
		this.equal = equal;
		this.not = not;
	}
	
	public Path<?> getPath(Path<?> entity) {
		String[] split = fieldName.split("\\.");
		for (String string : split) {
			entity = entity.get(string);
		}
		return entity;
	}
	
	public void addFetch(String fieldName){
		if(fetch==null)
			this.fetch = new ArrayList<String>();
		fetch.add(fieldName);
	}

	public final String getFieldName() {
		return fieldName;
	}

	public final T getTerm() {
		return term;
	}

	public final boolean isEqual() {
		return equal;
	}

	public final boolean isNot() {
		return not;
	}

	public List<String> getFetch() {
		return fetch;
	}

}
