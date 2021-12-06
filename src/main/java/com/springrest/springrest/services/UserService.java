package com.springrest.springrest.services;

import com.springrest.springrest.shared.dto.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto userDto);
	
	UserDto getAllUsers();

}
