package com.mpx.birjan.service.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Status;

@Repository
public class GameDao extends AbstractJpaDAO<Game> {

	public List<Game> findByFilter(Lottery lottery, Status status,
			DateTime from, DateTime to) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Game> cq = cb.createQuery(Game.class);
		Root<Game> game = cq.from(Game.class);

		List<Predicate> list = new ArrayList<Predicate>();

		if (status != null)
			list.add(cb.equal(game.<Status> get("status"), status));
		if (lottery != null)
			list.add(cb.equal(game.<Lottery> get("lottery"), lottery));
		if (from != null && to != null)
			list.add(cb.between(game.<Date> get("created"), from.toDate(),
					to.toDate()));

		cq.where(cb.and(list.toArray(new Predicate[list.size()])));

		// cq.where(cb.equal(game.<Lottery>get("lottery"), lottery));
		//
		// Subquery<Game> sq = cq.subquery(Game.class);
		// Root<Game> gameSQ = sq.from(Game.class);
		// sq.where(cb.equal(gameSQ.<Status>get("status"), status));

		TypedQuery<Game> q = em.createQuery(cq);
		List<Game> all = q.getResultList();
		return all;

	}

}
