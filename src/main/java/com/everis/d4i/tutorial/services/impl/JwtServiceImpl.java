package com.everis.d4i.tutorial.services.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.everis.d4i.tutorial.entities.Users;
import com.everis.d4i.tutorial.json.UserLoginRest;
import com.everis.d4i.tutorial.repositories.UserRepository;
import com.everis.d4i.tutorial.services.JwtService;

@Service
public class JwtServiceImpl implements JwtService{
	
	public static final String STARTING = "Netflix";
	
	private static final String USER = "user";
	private static final String ROLES = "roles";
	private static final String ISSUER = "ntt-data";
	private static final int EXPIRES_IN_MILLISECOND = 3600000;
	private static final String SECRET = "secretC";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String createToken (UserLoginRest userLoginRest) {
		
		Users user = userRepository.findByUsername(userLoginRest.getUsername());
		
		if(user != null && passwordEncoder.matches(userLoginRest.getPassword(), user.getPassword())) {	
		
			List<String> roles = user.getRoles().stream().map(rol -> rol.getName()).collect(Collectors.toList());
			
			return JWT.create()
					.withIssuer(ISSUER)
					.withIssuedAt(new Date())
					.withNotBefore(new Date())
					.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_IN_MILLISECOND))
					.withClaim(USER, userLoginRest.getUsername())
					.withArrayClaim(ROLES, roles.toArray(new String[0]))
					.sign(Algorithm.HMAC256(SECRET));
			
		}else {
			throw new UsernameNotFoundException("Invalid username or password");
		}
	}
	
	public boolean isNetflix(String authorization) {
		return authorization != null && authorization.startsWith(STARTING) && authorization.split("\\.").length == 3;
	}
	
	public DecodedJWT verify(String authorization){
		if(!this.isNetflix(authorization)) {
			throw new JWTVerificationException("It is not Netflix");
		}
		try {
			return JWT.require(Algorithm.HMAC256(SECRET))
					.withIssuer(ISSUER).build()
					.verify(authorization.substring(STARTING.length()+1));
		} catch (Exception e) {
			throw new JWTVerificationException("JWT is wrong" + e.getMessage());
		}
	}
	
	public String user(String authorization){
		return this.verify(authorization).getClaim(USER).asString();
	}

	public List<String> roles(String authorization){
		return Arrays.asList(this.verify(authorization).getClaim(ROLES).asArray(String.class));
	}
}
