package com.springrest.springrest.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.springrest.springrest.shared.dto.PageDto;
import com.springrest.springrest.shared.dto.UserDto;

public interface UserService extends UserDetailsService {

	UserDto createUser(UserDto userDto);

	List<UserDto> createUserInBulk(List<UserDto> userList);

	PageDto getAllUsers(int page, int size, String sortby, Sort.Direction sortOrder);

	UserDto getUserById(String userId);

	UserDto getUserByEmail(String email);

	UserDto updateUser(UserDto userDto, String userId);

	void deleteUser(String userId);

}
