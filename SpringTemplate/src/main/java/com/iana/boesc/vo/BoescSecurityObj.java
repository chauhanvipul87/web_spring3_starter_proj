package com.iana.boesc.vo;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component("securityObj")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)  
public class BoescSecurityObj {
	
	private String userName;
    private Integer userid;
	private String scac;
    private String usDOT;
	private String userType;
	private String firstName;
	private String lastName;
	private Integer loginId;
	private String companyName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getScac() {
		return scac;
	}
	public void setScac(String scac) {
		this.scac = scac;
	}
	public String getUsDOT() {
		return usDOT;
	}
	public void setUsDOT(String usDOT) {
		this.usDOT = usDOT;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getLoginId() {
		return loginId;
	}
	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public String toString() {
		return "BoescSecurityObj [userName=" + userName + ", userid=" + userid
				+ ", scac=" + scac + ", usDOT=" + usDOT + ", userType="
				+ userType + ", firstName=" + firstName + ", lastName="
				+ lastName + ", loginId=" + loginId + ", companyName="
				+ companyName + "]";
	}

	  
	  
	  
}
