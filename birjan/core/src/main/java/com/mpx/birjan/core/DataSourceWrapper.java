package com.mpx.birjan.core;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;

public class DataSourceWrapper extends TransactionAwareDataSourceProxy {
	
	public DataSourceWrapper(JpaTransactionManager jpaTransactionManager) {
		super(jpaTransactionManager.getDataSource());
	}

}
