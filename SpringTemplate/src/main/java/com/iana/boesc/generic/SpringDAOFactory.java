package com.iana.boesc.generic;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iana.boesc.dao.BadOrdersDAO;
import com.iana.boesc.dao.BadOrdersDAOImpl;
import com.iana.boesc.utility.EmailUtility;

@Service
public class SpringDAOFactory extends DAOFactory {

	@Autowired
	private DataSource dataSource;
	Logger log = Logger.getLogger(SpringDAOFactory.class);
	
	@SuppressWarnings("rawtypes")
	private Object instantiateDAO(Class<?> daoClass) {
		try {
			Class[] argsClass = new Class[] { DataSource.class };
			return Class.forName(daoClass.getName()).getConstructor(argsClass).newInstance(this.dataSource);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error( "Excpetion Raised in instantiateDAO: Can not instantiate DAO:", ex);
	    	 Map<String,Object> inputParamMap = new HashMap<String,Object>();
	    	 inputParamMap.put("daoClass", daoClass.getName());
	    	 EmailUtility.prepareAndSendExceptionEmail(null,ex,inputParamMap);
			throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
		}
	}

	@Override
	public BadOrdersDAO getBadOrdersDAOFacade() {
		return (BadOrdersDAO) instantiateDAO(BadOrdersDAOImpl.class);
	}
}
