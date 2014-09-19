package com.iana.boesc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.iana.boesc.vo.BoescSecurityObj;

/**
 * Handles requests for the login page request.
 */
@Controller
public class LoginController {
	
	@Autowired
	private BoescSecurityObj securityObj;
	
	
}
