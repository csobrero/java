package com.mpx.birjan.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.annotation.Autowired;

import com.mpx.birjan.service.dao.IGenericDAO;

public abstract class AbstractService<T extends Serializable> {

	private IGenericDAO<T> dao;

	@Autowired
	public void setDao(final IGenericDAO<T> dao) {
		this.dao = dao;
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		@SuppressWarnings("unchecked")
		Class<T> t = ((Class<T>) genericSuperclass.getActualTypeArguments()[0]);
		this.dao.setClazz(t);
	}

	public IGenericDAO<T> getDao() {
		return dao;
	}

}
