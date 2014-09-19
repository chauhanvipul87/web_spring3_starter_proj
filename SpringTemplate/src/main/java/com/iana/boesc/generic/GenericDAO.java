package com.iana.boesc.generic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.iana.boesc.utility.EmailUtility;



public abstract class GenericDAO implements IGenericDAO {
	
	Logger log = Logger.getLogger(GenericDAO.class);
	
	
	/* Methods :: SpringJdbcTemplate Start ...........................................................................................................*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> List<T>  findAll(DataSource dataSource, String query,Class<T> requiredType,Object... params) throws Exception {
			return getSpringJdbcTemplate(dataSource).query(query,new BeanPropertyRowMapper(requiredType),params);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> List<T>  findAll(DataSource dataSource,String query,Class<T> requiredType) throws Exception {
			return getSpringJdbcTemplate(dataSource).query(query,new BeanPropertyRowMapper(requiredType));
	}
	
	@Override
	public <T> List<T>  findAll(DataSource dataSource,String query,Object[] params,Class<T> requiredType) throws Exception {
		return findAll(dataSource, query, requiredType, params);
	}
	
	@Override
	public List<Map<String,Object>>  findList(DataSource dataSource,String query,Object... params) throws Exception {
			return getSpringJdbcTemplate(dataSource).queryForList(query, params);
	}
	@Override
	public List<Map<String, Object>> findList(DataSource dataSource,Object[] params, String query) throws Exception {
		return findList(dataSource, query, params);
	}
	
	@Override
	public <T> List<T> findList(DataSource dataSource,Object[] params, String query,Class<T> requiredType) throws Exception {
		return getSpringJdbcTemplate(dataSource).queryForList(query, requiredType, params);
	}
	
	
	@Override
	public List<Map<String,Object>> findList(DataSource dataSource,String query) throws Exception {
			return getSpringJdbcTemplate(dataSource).queryForList(query);
	}
	
	@Override
	public Map<String,Object>  findMap(DataSource dataSource,String query,Object... params) throws Exception {
			return getSpringJdbcTemplate(dataSource).queryForMap(query, params);
	}
	
	@Override
	public Map<String, Object> findMap(DataSource dataSource, Object[] params,String query) throws Exception {
		return findMap(dataSource, query, params);
	}
	
	@Override
	public Map<String,Object>  findMap(DataSource dataSource,String query) throws Exception {
			return getSpringJdbcTemplate(dataSource).queryForMap(query);
	}
	
	@Override
	public <T> T findObject(DataSource dataSource ,String query,Class<T> requiredType) throws Exception {
			return getSpringJdbcTemplate(dataSource).queryForObject(query, requiredType);
	}
	
	@Override
	public <T> T findObject(DataSource dataSource ,String query,Class<T> requiredType,Object... params) throws Exception {
			return getSpringJdbcTemplate(dataSource).queryForObject(query, requiredType,params);
	}
	
	@Override
	public <T> T findObject(DataSource dataSource,String query,Object[] params,Class<T> requiredType) throws Exception {
		return findObject(dataSource, query, requiredType, params);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T  findBean(DataSource dataSource,String query,Class<T> requiredType,Object... params) throws Exception {
			T object =null;
			if(params ==null || params.length ==0){
				throw new Exception("Please send valid params values, it should not be empty or null.");
			}else{
				object =(T)getSpringJdbcTemplate(dataSource).query(query,new BeanPropertyRowMapper(requiredType),params);
			}
			return object;
	}
	
	@Override
	public <T> T  findBean(DataSource dataSource,String query,Object[] params,Class<T> requiredType) throws Exception {
		return findBean(dataSource, query, requiredType, params);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T  findBean(DataSource dataSource,String query,Class<T> requiredType) throws Exception {
			T object =(T)getSpringJdbcTemplate(dataSource).query(query,new BeanPropertyRowMapper(requiredType));
			return object;
	}
		
	@Override
	public Long findTotalRecordCount(DataSource dataSource, String query,Object... params) throws Exception{
		Long count = 0l;
		if(params ==null || params.length ==0){
			count =  getSpringJdbcTemplate(dataSource).queryForObject(query,Long.class);
		}else{
			count =  getSpringJdbcTemplate(dataSource).queryForObject(query,Long.class,params);
		}
		return count;
	}
	
	@Override
	public Long findTotalRecordCount(DataSource dataSource, String query) throws Exception{
		Long count = 0l;
		count =  getSpringJdbcTemplate(dataSource).queryForObject(query,Long.class);
		return count;
	}
	
	@Override
	public void rollBackTransaction(PlatformTransactionManager transactionManager,TransactionStatus status) {
		transactionManager.rollback(status);
	}
	
	@Override
	public void commitTransaction(PlatformTransactionManager transactionManager,TransactionStatus status) {
		transactionManager.commit(status);
	}
	
	@Override
	public TransactionStatus beginTransAndGetStatus(PlatformTransactionManager transactionManager) {
	    TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		return status;
	}
	
	@Override
	public JdbcTemplate getSpringJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource); 
	}
	
	@Override
	public PlatformTransactionManager getTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource) ; 
	}
	
	@Override
	public int saveOrUpdate(DataSource dataSource, String query,boolean enableTransMgmt)throws Exception {
		int affectedRows = 0;
		if(enableTransMgmt){
			affectedRows = getSpringJdbcTemplate(dataSource).update(query);
			return affectedRows;
		
		}else{
			PlatformTransactionManager transactionManager = getTransactionManager(dataSource);
			TransactionStatus status = beginTransAndGetStatus(transactionManager);
			try {
				affectedRows = getSpringJdbcTemplate(dataSource).update(query);
				if(affectedRows >0)
					commitTransaction(transactionManager, status);
				else
					rollBackTransaction(transactionManager, status);
			} catch (Exception e) {
				log.error( "Excpetion Raised in saveOrUpdate(DataSource dataSource, String query,boolean enableTransMgmt): ", e);
		    	 Map<String,Object> inputParamMap = new HashMap<String,Object>();
		    	 inputParamMap.put("query", query);
		    	 inputParamMap.put("enableTransMgmt", enableTransMgmt);
		    	 EmailUtility.prepareAndSendExceptionEmail(null,e,inputParamMap);
				rollBackTransaction(transactionManager, status);
				throw e;
			}
			return affectedRows;
		}
	}
	
	@Override
	public int saveOrUpdate(DataSource dataSource, String query,boolean enableTransMgmt,Object... params)throws Exception {
		int affectedRows = 0;
		if(enableTransMgmt){
				affectedRows = getSpringJdbcTemplate(dataSource).update(query,params);
				return affectedRows;
		}else{
			PlatformTransactionManager transactionManager = getTransactionManager(dataSource);
			TransactionStatus status = beginTransAndGetStatus(transactionManager);
			try {
				affectedRows = getSpringJdbcTemplate(dataSource).update(query,params);
				if(affectedRows >0)
					commitTransaction(transactionManager, status);
				else
					rollBackTransaction(transactionManager, status);
			} catch (Exception e) {
				log.error( "Excpetion Raised in saveOrUpdate(DataSource dataSource, String query,boolean enableTransMgmt,Object... params): ", e);
				 Map<String,Object> inputParamMap = new HashMap<String,Object>();
		    	 inputParamMap.put("query", query);
		    	 inputParamMap.put("enableTransMgmt", enableTransMgmt);
		    	 inputParamMap.put("params", params);
		    	 EmailUtility.prepareAndSendExceptionEmail(null,e,inputParamMap);
				rollBackTransaction(transactionManager, status);
				throw e;
			}
			return affectedRows;
		}
		
	}
	
	@Override
	public int delete(DataSource dataSource, String query,boolean enableTransMgmt) throws Exception {
		 return saveOrUpdate(dataSource, query,enableTransMgmt);
	}
	
	@Override
	public int delete(DataSource dataSource, String query,boolean enableTransMgmt, Object... params)throws Exception {
		 return saveOrUpdate(dataSource, query,enableTransMgmt,params);
	}
	
	/* Methods :: SpringJdbcTemplate Ends .....................................................................................................................*/
	
	/* Methods :: NamedParameterJdbcTemplate Starts ...........................................................................................................*/
	
	@Override
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(DataSource dataSource) {
		return new  NamedParameterJdbcTemplate(dataSource);
	}
	
	/** 
	 	If you set column values in bean then send object instance which contains values. 
	 	In case where there no column value required to be send then simple pass ClassName.class 
	 	For Ex: 
	 	query = "INSERT INTO USERLOGIN (USER_ID)  VALUES ('80') ";
	 	saveOrUpdateBeanUsingNamedJDBC(dataSource, query,Login.class);
	 	
	 	If contains Value : then pass object of Login class
	 	Login login = new Login();
		login.setUserId("25");
		query = "INSERT INTO USERLOGIN (USER_ID)  VALUES (:userId) ";
		saveOrUpdateBeanUsingNamedJDBC(dataSource, query,login);
	 	 
	 
	 */
	@Override
	public <T> int saveOrUpdateBeanUsingNamedJDBC(DataSource dataSource,String query,T obj,boolean enableTransMgmt)throws Exception {
		int affectedRows = 0;
		if(enableTransMgmt){
			try {
				SqlParameterSource sqlParams = new BeanPropertySqlParameterSource(obj);
				affectedRows = getNamedParameterJdbcTemplate(dataSource).update(query,sqlParams);
				return affectedRows;
			} catch (Exception e) {
				log.error( "Excpetion Raised in saveOrUpdateBeanUsingNamedJDBC(DataSource dataSource,String query,T obj,boolean enableTransMgmt)", e);
				 Map<String,Object> inputParamMap = new HashMap<String,Object>();
		    	 inputParamMap.put("query", query);
		    	 inputParamMap.put("enableTransMgmt", enableTransMgmt);
		    	 inputParamMap.put("obj", obj);
		    	 EmailUtility.prepareAndSendExceptionEmail(null,e,inputParamMap);
				throw e;
			}
		}else{
			PlatformTransactionManager transactionManager = getTransactionManager(dataSource);
			TransactionStatus status = beginTransAndGetStatus(transactionManager);
			try {
				SqlParameterSource sqlParams = new BeanPropertySqlParameterSource(obj);
				affectedRows = getNamedParameterJdbcTemplate(dataSource).update(query,sqlParams);
				if(affectedRows > 0)
					commitTransaction(transactionManager, status);
				else
					rollBackTransaction(transactionManager, status);
			} catch (Exception e) {
				log.error( "Excpetion Raised in saveOrUpdateBeanUsingNamedJDBC(DataSource dataSource,String query,T obj,boolean enableTransMgmt)", e);
				rollBackTransaction(transactionManager, status);
				 Map<String,Object> inputParamMap = new HashMap<String,Object>();
		    	 inputParamMap.put("query", query);
		    	 inputParamMap.put("enableTransMgmt", enableTransMgmt);
		    	 inputParamMap.put("obj", obj);
		    	 EmailUtility.prepareAndSendExceptionEmail(null,e,inputParamMap);
				throw e;
			}
			
			return affectedRows;
		}
		
	}
	
	@Override
	public <T> T findObjectUsingNamedJDBC(DataSource dataSource, String query,Class<T> requiredType,Object obj)throws Exception {
			SqlParameterSource sqlParams = new BeanPropertySqlParameterSource(obj);
			T t=  (T) getNamedParameterJdbcTemplate(dataSource).queryForObject(query,sqlParams,requiredType);
			return t;
	}
	
	@Override
	public  Map<String, Object> findMapUsingNamedJDBC(DataSource dataSource, String query,Object obj )throws Exception {
			SqlParameterSource sqlParams = new BeanPropertySqlParameterSource(obj);
			return getNamedParameterJdbcTemplate(dataSource).queryForMap(query,sqlParams);
	}
	@Override
	public  List<Map<String,Object>> findAllUsingNamedJDBC(DataSource dataSource, String query,Object obj )throws Exception {
			SqlParameterSource sqlParams = new BeanPropertySqlParameterSource(obj);
			return getNamedParameterJdbcTemplate(dataSource).queryForList(query,sqlParams);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> List<T>  findAllUsingNamedJDBC(DataSource dataSource,String query,Class<T> requiredType,T obj) throws Exception {
			SqlParameterSource sqlParams = new BeanPropertySqlParameterSource(obj);
			return getNamedParameterJdbcTemplate(dataSource).query(query, sqlParams, new BeanPropertyRowMapper(requiredType));
	}
	
	public <T> long saveAndGetAutoGeneratedKeyUsingNamedJDBC(DataSource dataSource,String query,Class<T> requiredType,T obj) {
			SqlParameterSource fileParameters = new BeanPropertySqlParameterSource(obj);
		    KeyHolder keyHolder = new GeneratedKeyHolder();
		    getNamedParameterJdbcTemplate(dataSource).update(query, fileParameters, keyHolder);
		    return keyHolder.getKey().longValue();
   }
	
	@Override
	public <T> int deleteUsingNamedJDBC(DataSource dataSource,String query,T obj,boolean enableTransMgmt)throws Exception {
		return saveOrUpdateBeanUsingNamedJDBC(dataSource, query, obj,enableTransMgmt);
	}
	
	/* Methods :: NamedParameterJdbcTemplate Ends ...........................................................................................................*/
	

	
	@Override
	public boolean resetSQL(StringBuilder sql) {
		sql.setLength(0);	
		return true;
	}

	protected <T> T newInstance(Class<T> c) throws Exception {
        return c.newInstance();

	}
	
}
