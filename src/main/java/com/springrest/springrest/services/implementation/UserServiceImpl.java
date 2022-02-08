package com.springrest.springrest.services.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrest.springrest.crud.UserRepository;
import com.springrest.springrest.customexception.UserException;
import com.springrest.springrest.io.entity.UserEntity;
import com.springrest.springrest.services.UserService;
import com.springrest.springrest.shared.Utils;
import com.springrest.springrest.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Override
	public UserDto createUser(UserDto user) {
		
		if(userRepository.findByEmail(user.getEmail()) != null) 
			throw new UserException("User already Exist");
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		String publicUserId=utils.createNewUserId();

		userEntity.setEncryptedPassword("test");
		userEntity.setUserId(publicUserId);

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
