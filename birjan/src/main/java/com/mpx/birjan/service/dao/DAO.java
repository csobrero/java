package com.mpx.birjan.service.dao;

import java.io.Serializable;
import java.util.List;

interface DAO<T extends Serializable> {

	T getById(final Long id);

	List<T> getAll();

	void create(final T entity);

	void update(final T entity);

	void delete(final T entity);

	void deleteById(final Long entityId);

	Class<T> getClazz();

}