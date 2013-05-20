package com.mpx.birjan.bean;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Wager.class)
public abstract class Wager_ extends com.mpx.birjan.bean.AbstractEntity_ {

	public static volatile ListAttribute<Wager, Game> games;
	public static volatile SingularAttribute<Wager, Person> person;
	public static volatile SingularAttribute<Wager, Float> betAmount;
	public static volatile SingularAttribute<Wager, String> hash;
	public static volatile SingularAttribute<Wager, User> user;
	public static volatile SingularAttribute<Wager, Float> winAmount;

}

