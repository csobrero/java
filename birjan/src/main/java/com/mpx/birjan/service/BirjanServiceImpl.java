package com.mpx.birjan.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Person;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.service.dao.IGenericDAO;
import com.mpx.birjan.service.dao.PersonDao;

@Service
public class BirjanServiceImpl implements IBirjanService{

	private IGenericDAO<Game> gameDao;

	private IGenericDAO<Wager> wagerDao;

	@Autowired
	private PersonDao personDao;

	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void processWinners(Lottery lottery) {
		

	}

	@Resource(name = "genericJpaDAO")
	public final void gameDao(final IGenericDAO<Game> daoToSet) {
		gameDao = daoToSet;
		gameDao.setClazz(Game.class);
	}

	@Resource(name = "genericJpaDAO")
	public final void wagerDao(final IGenericDAO<Wager> daoToSet) {
		wagerDao = daoToSet;
		wagerDao.setClazz(Wager.class);
	}

}
