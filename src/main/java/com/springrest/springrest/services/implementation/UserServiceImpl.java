package com.springrest.springrest.services.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springrest.springrest.crud.UserRepository;
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
	
	@Autowired
	BCryptPasswordEncoder bcryptPassword;
	
	@Override
	public UserDto createUser(UserDto user) {
		
		if(userRepository.findByEmail(user.getEmail()) != null) 
			throw new RuntimeException("User already Exist");
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		String publicUserId=utils.createNewUserId();

		userEntity.setEncryptedPassword(bcryptPassword.encode(user.getPassword()));
		userEntity.setUserId(publicUserId);

		UserEntity savedUser = userRepository.save(userEntity);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(savedUser, returnValue);

		return returnValue;
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<UserDto> data= new ArrayList<UserDto>();
	
		Iterable<UserEntity> users= userRepository.findAll();
		users.forEach(item->{
			UserDto returnValue = new UserDto();
			BeanUtils.copyProperties(item, returnValue);
			data.add(returnValue);
		});
			
		return data;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	};

}
