package com.iana.boesc.generic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.iana.boesc.vo.BoescSecurityObj;

public class SessionInterceptor extends HandlerInterceptorAdapter  {

	public static final String LOGIN_URL ="login.do";
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,Object obj) throws Exception {
		
		String requrestURL = req.getRequestURL() !=null &&  !req.getRequestURL().toString().equals("") ? req.getRequestURL().toString() :"";
		if(!requrestURL.equals("")){
			String controllerURL = req.getRequestURL().substring(req.getRequestURL().toString().lastIndexOf("/")+1, req.getRequestURL().toString().length());
			if(getSkippedUrls().contains(controllerURL)){
				return true;
			}else{
				//check session 
				HttpSession session =  req.getSession();
				System.out.println("session "+session.getAttribute("securityObj"));
				if(session !=null && session.getAttribute("securityObj")!=null ){
					BoescSecurityObj securityObj = (BoescSecurityObj) session.getAttribute("securityObj");
					if(securityObj !=null && securityObj.getUserName()!=null && securityObj.getUserid()!=null){
						return true;
					}else{
						req.getRequestDispatcher(LOGIN_URL).forward(req, res);
					}
				}else{
					req.getRequestDispatcher(LOGIN_URL).forward(req, res);
				}
			}
		}
		return false;
	}
	
	/*@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res,	Object obj, ModelAndView model) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest req,	HttpServletResponse res, Object obj, Exception ex) throws Exception {
		
	}*/

	
	private List<String> getSkippedUrls(){
		List<String> skippedUrlList = new ArrayList<String>();
		skippedUrlList.add(LOGIN_URL);
		skippedUrlList.add("home.do");
		skippedUrlList.add("error500.do");
		return skippedUrlList;
	}

}
