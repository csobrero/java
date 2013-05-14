package com.mpx.birjan.service.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T extends Serializable> {

	T getById(final long id);

	List<T> getAll();

	void create(final T entity);

	void update(final T entity);

	void updateAll(final List<T> entities);
	
	void delete(final T entity);

	void deleteById(final long entityId);

	Class<T> getClazz();

	void setClazz(Class<T> classToSet);

}