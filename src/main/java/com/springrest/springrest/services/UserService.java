package com.springrest.springrest.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.springrest.springrest.shared.dto.UserDto;

public interface UserService extends UserDetailsService {

	UserDto createUser(UserDto userDto);

	List<UserDto> createUserInBulk(List<UserDto> userList);

	List<UserDto> getAllUsers();

	UserDto getUserById(String userId);

	UserDto updateUser(UserDto userDto, String userId);

	void deleteUser(String userId);

}
