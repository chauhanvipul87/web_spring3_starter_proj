package com.iana.boesc.dao;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.iana.boesc.generic.GenericDAO;

@Repository
public class BadOrdersDAOImpl extends GenericDAO implements BadOrdersDAO{

	@SuppressWarnings("unused")
	private DataSource dataSource;
    Logger log =  Logger.getLogger(BadOrdersDAOImpl.class);
	public BadOrdersDAOImpl(){
	}
	
	public BadOrdersDAOImpl(DataSource ds){
		this.dataSource = ds;
	}
	
	
}
