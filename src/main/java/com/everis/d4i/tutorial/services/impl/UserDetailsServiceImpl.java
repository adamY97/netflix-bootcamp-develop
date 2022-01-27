//package com.everis.d4i.tutorial.services.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.User.UserBuilder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.everis.d4i.tutorial.entities.Users;
//import com.everis.d4i.tutorial.repositories.UserRepository;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService{
//
//	@Autowired
//	private UserRepository userRepository;
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		
//		Users usuario = userRepository.findByUsername(username);
//		UserBuilder builder = null;
//		
//		if(usuario != null) {
//			
//			builder = User.withUsername(username);
//			builder.disabled(false);
//			builder.password(usuario.getPassword());
//			builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
//			
//		}else {
//			
//			throw new UsernameNotFoundException("Usuario no encontrado");
//		}
//		
//		return builder.build();
//	}
//
//}
