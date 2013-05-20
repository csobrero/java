package com.mpx.birjan.service.dao;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class Filter<T> {

	private String fieldName;

	private T term;

	private boolean isEqualSearch;

	public Filter(String fieldName, T term) {
		this(fieldName, term, true);
	}

	public Filter(String fieldName, T term, boolean isEqualSearch) {
		this.fieldName = fieldName;
		this.term = term;
		this.isEqualSearch = isEqualSearch;
	}
	
	public Path<?> getPath(Root<?> entity) {
		return entity.<T> get(fieldName);
	}

	public final String getFieldName() {
		return fieldName;
	}

	public final T getTerm() {
		return term;
	}

	public final boolean isEqualSearch() {
		return isEqualSearch;
	}

}
