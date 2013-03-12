package com.mpx.birjan.service.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractJpaDAO<T extends Serializable> implements
		IGenericDAO<T> {

	private Class<T> clazz;

	@PersistenceContext
	EntityManager em;

	@Override
	public T getById(final Long id) {
		return this.em.find(this.getClazz(), id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return this.em.createQuery("from " + this.getClazz().getName())
				.getResultList();
	}

	@Override
	public void create(final T entity) {
		this.em.persist(entity);
	}

	@Override
	public void update(final T entity) {
		this.em.merge(entity);
	}

	@Override
	public void delete(final T entity) {
		this.em.remove(entity);
	}

	@Override
	public void deleteById(final Long entityId) {
		final T entity = this.getById(entityId);

		this.delete(entity);
	}

	public void flush(T entity) {
		this.em.flush();
		// return entity;
	}

	public void refresh(T entity) {
		this.em.refresh(entity);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<T> getClazz() {
		if (clazz == null) {
			ParameterizedType genericSuperclass = (ParameterizedType) getClass()
					.getGenericSuperclass();
			this.clazz = ((Class<T>) genericSuperclass.getActualTypeArguments()[0]);
		}
		return clazz;
	}

	@Override
	public void setClazz(final Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}
}
