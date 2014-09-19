package com.iana.boesc.generic;

import org.springframework.stereotype.Service;

import com.iana.boesc.dao.BadOrdersDAO;

@Service
public abstract class DAOFactory {
	public abstract BadOrdersDAO getBadOrdersDAOFacade();
}
