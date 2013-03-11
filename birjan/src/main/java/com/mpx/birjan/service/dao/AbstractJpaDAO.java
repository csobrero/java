package com.mpx.birjan.service.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractJpaDAO<T extends Serializable> implements
		IGenericDAO<T> {

	private Class<T> clazz;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public T getById(final Long id) {
		return this.entityManager.find(this.getClazz(), id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return this.entityManager.createQuery(
				"from " + this.getClazz().getName()).getResultList();
	}

	@Override
	public void create(final T entity) {
		this.entityManager.persist(entity);
	}

	@Override
	public void update(final T entity) {
		this.entityManager.merge(entity);
	}

	@Override
	public void delete(final T entity) {
		this.entityManager.remove(entity);
	}

	@Override
	public void deleteById(final Long entityId) {
		final T entity = this.getById(entityId);

		this.delete(entity);
	}

	public void flush(T entity) {
		this.entityManager.flush();
		// return entity;
	}

	public void refresh(T entity) {
		this.entityManager.refresh(entity);
	}

	@Override
	public Class<T> getClazz() {
		// if (clazz == null) {
		// ParameterizedType genericSuperclass = (ParameterizedType) getClass()
		// .getGenericSuperclass();
		// this.clazz = ((Class<T>)
		// genericSuperclass.getActualTypeArguments()[0]);
		// }
		return clazz;
	}

	@Override
	public void setClazz(final Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}
}
