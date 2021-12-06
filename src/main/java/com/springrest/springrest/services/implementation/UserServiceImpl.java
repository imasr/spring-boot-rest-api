package com.springrest.springrest.services.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrest.springrest.crud.UserRepository;
import com.springrest.springrest.io.entity.UserEntity;
import com.springrest.springrest.services.UserService;
import com.springrest.springrest.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);

		userEntity.setEncryptedPassword("test");
		userEntity.setUserId("testUiId");

		UserEntity savedUser = userRepository.save(userEntity);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(savedUser, returnValue);

		return returnValue;
	}

	@Override
	public UserDto getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	};

}
