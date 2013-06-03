package com.mpx.birjan.service.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericJpaDAO<T extends Serializable> extends AbstractJpaDAO<T>
		implements IGenericDAO<T> {
	//
	public List<T> findByFilter(Filter<?>... filters) {
		TypedQuery<T> q = buildTypedQuery(filters);
		List<T> results = q.getResultList();
		return results;
	}

	public T findUniqueByFilter(Filter<?>... filters) {
		TypedQuery<T> q = buildTypedQuery(filters);
		try {
			T t = q.getSingleResult();
			return t;
		} catch (NoResultException e) {
			return null;
		}
	}

	private TypedQuery<T> buildTypedQuery(Filter<?>[] filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getClazz());
		final Root<T> entity = cq.from(getClazz());

		List<Predicate> list = new ArrayList<Predicate>();
		for (Filter<?> filter : filters) {
			if(filter!=null && filter.getTerm()!=null)
				if (filter.isEqual()){
					Path<?> path = filter.getPath(entity);
					Object term = filter.getTerm();
					list.add(filter.isNot()?cb.notEqual(path, term):cb.equal(path, term));
				}
				else{
					Path<String> path = entity.<String> get(filter.getFieldName());
					String term = "%" + ((String) filter.getTerm()).trim() + "%";
					list.add(filter.isNot()?cb.notLike(path,term):cb.like(path,term));
				}
		}

		cq.where(cb.and(list.toArray(new Predicate[list.size()])));

		TypedQuery<T> q = em.createQuery(cq);
		return q;
	}
	
}
