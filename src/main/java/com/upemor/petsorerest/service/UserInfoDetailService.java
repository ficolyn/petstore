package com.upemor.petsorerest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.upemor.petsorerest.model.User;
import com.upemor.petsorerest.repository.UserRepository;

@Service
public class UserInfoDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(
				"User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(),
				getAuthorities(user));
	}
	
	private Collection<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities = AuthorityUtils.createAuthorityList(user.getRole());
		return authorities;
	}

}