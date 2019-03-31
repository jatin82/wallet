package com.poc.wallet.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poc.wallet.exception.PlatformException;
import com.poc.wallet.model.Token;
import com.poc.wallet.model.db.User;
import com.poc.wallet.service.JWTService;
import com.poc.wallet.util.Constants;

public class JWTFilter extends HandlerInterceptorAdapter{
	
	private static final Logger log = LoggerFactory.getLogger(JWTFilter.class);
	  
	@Autowired
	private JWTService jwtService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String jwttoken = request.getHeader(Constants.AUTHORIZATION);
		if (!ObjectUtils.isEmpty(jwttoken)) {
			Token token = new Token(Constants.AUTHORIZATION, jwttoken);
			User user = jwtService.parseAuthToken(token);
			if (user == null)
			{
				log.error("JWTFilter : Invalid Token");
				throw new PlatformException(HttpStatus.UNAUTHORIZED,Constants.INVALID_TOKEN);
			}
			request.setAttribute(Constants.USER, user);
			log.debug("JWTFilter : valid token with {}",user.getEmail());
			return true;
		}
		throw new PlatformException(HttpStatus.UNAUTHORIZED,Constants.MISSING_AUTH_HEADER);
	}

}
