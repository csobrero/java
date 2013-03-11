package com.mpx.birjan.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.annotation.Autowired;

import com.mpx.birjan.service.dao.IGenericDAO;

public class AbstractService<T extends Serializable> {

	private IGenericDAO<T> dao;

	@Autowired
	public final void setDao(final IGenericDAO<T> dao) {
		this.dao = dao;
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		Class<T> t = ((Class<T>) genericSuperclass.getActualTypeArguments()[0]);
		this.dao.setClazz(t);
	}

}
