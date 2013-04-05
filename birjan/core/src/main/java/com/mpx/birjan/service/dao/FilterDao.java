package com.mpx.birjan.service.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.mpx.birjan.bean.Draw;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Status;

@Repository
public class FilterDao {

	@PersistenceContext
	EntityManager em;

	public List<Game> findGameByFilter(Status status, Lottery lottery, Date date) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Game> cq = cb.createQuery(Game.class);
		Root<Game> entity = cq.from(Game.class);

		List<Predicate> list = new ArrayList<Predicate>();

		if (status != null)
			list.add(cb.equal(entity.<Status> get("status"), status));
		if (lottery != null)
			list.add(cb.equal(entity.<Lottery> get("lottery"), lottery));
		if (date != null)
			list.add(cb.equal(entity.<Date> get("date"), date));

		cq.where(cb.and(list.toArray(new Predicate[list.size()])));

		TypedQuery<Game> q = em.createQuery(cq);
		List<Game> all = q.getResultList();
		return all;

	}

	public Game findGameByHash(String hash) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Game> cq = cb.createQuery(Game.class);
		Root<Game> game = cq.from(Game.class);

		cq.where(cb.equal(game.<String> get("hash"), hash));

		TypedQuery<Game> q = em.createQuery(cq);
		try {
			return q.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

	public List<Draw> findDrawByFilter(Lottery lottery, Date date) {
		return findDrawByFilter(null, lottery, date);
	}

	public List<Draw> findDrawByFilter(Status status, Lottery lottery, Date date) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Draw> cq = cb.createQuery(Draw.class);
		Root<Draw> entity = cq.from(Draw.class);

		List<Predicate> list = new ArrayList<Predicate>();

		if (status != null)
			list.add(cb.equal(entity.<Status> get("status"), status));
		if (lottery != null)
			list.add(cb.equal(entity.<Lottery> get("lottery"), lottery));
		if (date != null)
			list.add(cb.equal(entity.<Date> get("date"), date));

		cq.where(cb.and(list.toArray(new Predicate[list.size()])));

		TypedQuery<Draw> q = em.createQuery(cq);
		return q.getResultList();

	}
}
