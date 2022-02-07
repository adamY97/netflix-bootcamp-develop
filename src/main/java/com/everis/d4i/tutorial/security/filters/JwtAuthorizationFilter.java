package com.everis.d4i.tutorial.security.filters;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.everis.d4i.tutorial.services.JwtService;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	public static final String AUTHORIZATION = "Authorization";
	
	@Autowired
	private JwtService jwtService;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
		
		String authHeader = request.getHeader(AUTHORIZATION);
		
		if(jwtService.isNetflix(authHeader)) {
			LogManager.getLogger(this.getClass().getName()).debug(">>> FILTER JWT...");
			List<GrantedAuthority> authorities = jwtService.roles(authHeader).stream()
					.map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtService.user(authHeader), null, authorities);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}
}
