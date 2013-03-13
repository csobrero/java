package com.mpx.birjan.service.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Person;
import com.mpx.birjan.bean.Status;

@Repository
public class GameDao extends AbstractJpaDAO<Game> {

	public List<Person> findByFilter(String name, String surname, String movile) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Game> cq = cb.createQuery(Game.class);
		Root<Game> game = cq.from(Game.class);

		cq.where(cb.equal(game.<Status>get("status"), Status.OPEN));
		
		Subquery<Game> sq = cq.subquery(Game.class);
		
		
		
//		List<Predicate> list = new ArrayList<Predicate>();
//		if (StringUtils.isNotBlank(name))
//			list.add(cb.like(person.<String>get("name"), "%"+name.trim()+"%"));
//		if (StringUtils.isNotBlank(surname))
//			list.add(cb.like(person.<String>get("surname"), "%"+surname.trim()+"%"));
//		if (StringUtils.isNotBlank(movile))
//			list.add(cb.like(person.<String>get("movile"), "%"+movile.trim()+"%"));
//
//		if (!list.isEmpty()) {
//			cq.where(cb.and(list.toArray(new Predicate[list.size()])));
//			
//			TypedQuery<Person> q = em.createQuery(cq);
//			List<Person> allPersons = q.getResultList();
//			
//			return allPersons;
//		}
		return null;

	}

}
