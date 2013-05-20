package com.mpx.birjan.bean;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Game.class)
public abstract class Game_ extends com.mpx.birjan.bean.AbstractEntity_ {

	public static volatile SingularAttribute<Game, Status> status;
	public static volatile SingularAttribute<Game, Float> prize;
	public static volatile SingularAttribute<Game, byte[]> data;
	public static volatile SingularAttribute<Game, Date> date;
	public static volatile SingularAttribute<Game, Lottery> lottery;
	public static volatile SingularAttribute<Game, Wager> wager;

}

