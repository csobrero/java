package com.mpx.birjan.service.dao;

import java.io.Serializable;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericJpaDAO<T extends Serializable> extends AbstractJpaDAO<T>
		implements IGenericDAO<T> {
	//
}
