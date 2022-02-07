package com.everis.d4i.tutorial.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.everis.d4i.tutorial.entities.Users;
import com.everis.d4i.tutorial.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		Users usuario = userRepository.findByUsername(username);

		List<String> roles = usuario.getRoles().stream().map(rol -> rol.getName()).collect(Collectors.toList());
		
		if(roles.contains("USER")) {
			
			return this.userBuilder(username, new BCryptPasswordEncoder().encode(usuario.getPassword()), "USER");
			
		}else if(roles.contains("ADMIN")){
			
			return this.userBuilder(username, new BCryptPasswordEncoder().encode(usuario.getPassword()), "ADMIN", "USER");
			
		}else {
			
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
	
	}
	
	private User userBuilder(String username, String password, String... roles) {
		
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		
		return new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

}
