package com.springrest.springrest.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.springrest.springrest.shared.dto.UserDto;

public interface UserService extends UserDetailsService{
	
	UserDto createUser(UserDto userDto);
	
	UserDto getAllUsers();
	
	

}
