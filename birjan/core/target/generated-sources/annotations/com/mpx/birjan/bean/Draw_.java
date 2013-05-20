package com.mpx.birjan.bean;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Draw.class)
public abstract class Draw_ extends com.mpx.birjan.bean.AbstractEntity_ {

	public static volatile SingularAttribute<Draw, String[]> numbers;
	public static volatile SingularAttribute<Draw, Status> status;
	public static volatile SingularAttribute<Draw, Date> date;
	public static volatile SingularAttribute<Draw, Lottery> lottery;
	public static volatile SingularAttribute<Draw, User> user;

}

