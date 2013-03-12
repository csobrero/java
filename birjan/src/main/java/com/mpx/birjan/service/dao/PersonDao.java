package com.mpx.birjan.service.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.bean.Person;

@Repository
public class PersonDao extends AbstractJpaDAO<Person> {

	public List<Person> findByFilter(String name, String surname, String movile) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Person> cq = cb.createQuery(Person.class);
		// EntityType<Person> entity = em.getMetamodel().entity(Person.class);
		Root<Person> person = cq.from(Person.class);

		List<Predicate> list = new ArrayList<Predicate>();
		if (StringUtils.isNotBlank(name))
			list.add(cb.like(person.<String>get("name"), "%"+name.trim()+"%"));
		if (StringUtils.isNotBlank(surname))
			list.add(cb.like(person.<String>get("surname"), "%"+surname.trim()+"%"));
		if (StringUtils.isNotBlank(movile))
			list.add(cb.like(person.<String>get("movile"), "%"+movile.trim()+"%"));

		if (!list.isEmpty()) {
			cq.where(cb.and(list.toArray(new Predicate[list.size()])));
			
			TypedQuery<Person> q = em.createQuery(cq);
			List<Person> allPersons = q.getResultList();
			
			return allPersons;
		}
		return null;

	}

}
