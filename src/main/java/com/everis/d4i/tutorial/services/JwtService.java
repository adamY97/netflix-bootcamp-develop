package com.everis.d4i.tutorial.services;

import java.util.List;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.everis.d4i.tutorial.json.UserLoginRest;


public interface JwtService {

	String createToken (UserLoginRest userLoginRest);
	
	boolean isNetflix(String authorization);
	
	DecodedJWT verify(String authorization);
	
	String user(String authorization);
	
	List<String> roles(String authorization);
}
