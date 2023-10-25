package com.fashionrental.website.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fashionrental.website.model.CustomUserDetail;
import com.fashionrental.website.model.Product;
import com.fashionrental.website.model.User;
import com.fashionrental.website.repository.UserRepository;


@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findUserByEmail(email);
		user.orElseThrow(()->new UsernameNotFoundException("User is not found!!!"));
		return user.map(CustomUserDetail :: new).get();
	}	
	public Optional<User> findByEmail(String email) {
		Optional<User> user = userRepository.findUserByEmail(email);
		return user;
	}
}
