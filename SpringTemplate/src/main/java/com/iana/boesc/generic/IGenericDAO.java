package com.iana.boesc.generic;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;


public interface IGenericDAO {

	public JdbcTemplate getSpringJdbcTemplate(DataSource dataSource);
	public TransactionStatus beginTransAndGetStatus(PlatformTransactionManager transactionManager);
	public void rollBackTransaction(PlatformTransactionManager transactionManager,TransactionStatus status); 
	public void commitTransaction(PlatformTransactionManager transactionManager,TransactionStatus status);
	public PlatformTransactionManager getTransactionManager(DataSource dataSource);	
	
	public <T> List<T> findAll(DataSource dataSource, String query,Class<T> requiredType,Object... params) throws Exception;
	public <T> List<T> findAll(DataSource dataSource, String query,Object[] params,Class<T> requiredType) throws Exception;
	public <T> List<T> findAll(DataSource dataSource, String query,Class<T> requiredType ) throws Exception;
	
	public List<Map<String, Object>> findList(DataSource dataSource, String query,Object... params) throws Exception;
	public List<Map<String, Object>> findList(DataSource dataSource,Object[] params, String query) throws Exception;
	public List<Map<String, Object>> findList(DataSource dataSource, String query)	throws Exception;
	
	public Map<String, Object> findMap(DataSource dataSource, String query,Object... params) throws Exception;
	public Map<String, Object> findMap(DataSource dataSource, String query)throws Exception;
	public Map<String, Object> findMap(DataSource dataSource,Object[] params,String query) throws Exception;
	
	
	public <T> T findBean(DataSource dataSource, String query,Class<T> requiredType,Object... params) throws Exception;
	public <T> T findBean(DataSource dataSource, String query,Class<T> requiredType) throws Exception;
	public <T> T findBean(DataSource dataSource, String query,Object[]params,Class<T> requiredType) throws Exception;
	
	
	public <T> T findObject(DataSource dataSource, String query, Class<T> requiredType)throws Exception;
	public <T> T findObject(DataSource dataSource, String query, Class<T> requiredType,Object... params) throws Exception;
	public <T> T findObject(DataSource dataSource, String query, Object[]params,Class<T> requiredType) throws Exception;
	
	
	public Long findTotalRecordCount(DataSource dataSource, String query,Object... params) throws Exception;
	public Long findTotalRecordCount(DataSource dataSource, String query) throws Exception;
	
	boolean resetSQL(StringBuilder sql);
	
	public  int saveOrUpdate(DataSource dataSource, String query,boolean enableTransMgmt) throws Exception;
	public  int saveOrUpdate(DataSource dataSource, String query,boolean enableTransMgmt,Object... params) throws Exception;
	public  int delete(DataSource dataSource, String query,boolean enableTransMgmt) throws Exception;
	public  int delete(DataSource dataSource, String query,boolean enableTransMgmt,Object... params) throws Exception;
	
	
	//NamedParameterJdbc Template related methods.
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(DataSource dataSource);
	public <T> int saveOrUpdateBeanUsingNamedJDBC(DataSource dataSource,	String query,T obj ,boolean enableTransMgmt) throws Exception;
	public <T> int deleteUsingNamedJDBC(DataSource dataSource,	String query,T obj ,boolean enableTransMgmt) throws Exception;
		
	public <T> T findObjectUsingNamedJDBC(DataSource dataSource ,String query,Class<T> requiredType, Object obj) throws Exception;
	public Map<String, Object> findMapUsingNamedJDBC(DataSource dataSource,	String query, Object obj) throws Exception;
	public List<Map<String, Object>> findAllUsingNamedJDBC(DataSource dataSource,String query, Object obj) throws Exception;
	public <T> List<T> findAllUsingNamedJDBC(DataSource dataSource,String query, Class<T> requiredType,T obj) throws Exception;
	public <T> List<T> findList(DataSource dataSource, Object[] params, String query,Class<T> requiredType) throws Exception;
	
	
	
	
}
