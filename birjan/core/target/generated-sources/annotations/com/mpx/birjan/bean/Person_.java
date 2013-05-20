package com.mpx.birjan.bean;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ extends com.mpx.birjan.bean.AbstractEntity_ {

	public static volatile SingularAttribute<Person, String> movile;
	public static volatile SingularAttribute<Person, String> name;
	public static volatile SingularAttribute<Person, String> surname;
	public static volatile ListAttribute<Person, Wager> wagers;

}

