package com.mpx.birjan.service.dao;

import javax.persistence.criteria.Path;

public class Filter<T> {

	private String fieldName;

	private T term;

	private boolean equal;
	
	private boolean not;

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

}
