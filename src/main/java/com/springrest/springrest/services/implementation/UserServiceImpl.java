package com.springrest.springrest.services.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springrest.springrest.io.entity.UserEntity;
import com.springrest.springrest.repositories.UserRepository;
import com.springrest.springrest.services.UserService;
import com.springrest.springrest.shared.dto.UserDto;
import com.springrest.springrest.shared.helper.Utils;

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

		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new UsernameNotFoundException("User already Exist");

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		String publicUserId = utils.createNewUserId();
		userEntity.setUserId(publicUserId);

		userEntity.setEncryptedPassword(bcryptPassword.encode(user.getPassword()));

		UserEntity savedUser = userRepository.save(userEntity);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(savedUser, returnValue);

		return returnValue;
	}

	@Override
	public List<UserDto> createUserInBulk(List<UserDto> userList) {
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();

		userList.forEach(user -> {
			UserEntity userEntity = new UserEntity();
			BeanUtils.copyProperties(user, userEntity);

			String publicUserId = utils.createNewUserId();
			userEntity.setEncryptedPassword(bcryptPassword.encode(user.getPassword()));
			userEntity.setUserId(publicUserId);
			userEntityList.add(userEntity);
		});

		Iterable<UserEntity> savedUserList = userRepository.saveAll(userEntityList);
		List<UserDto> returnValue = new ArrayList<UserDto>();
		savedUserList.forEach(savedUser -> {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(savedUser, userDto);
			returnValue.add(userDto);
		});

		return returnValue;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<UserDto> data = new ArrayList<UserDto>();

		Iterable<UserEntity> users = userRepository.findAll();
		users.forEach(item -> {
			UserDto returnValue = new UserDto();
			BeanUtils.copyProperties(item, returnValue);
			data.add(returnValue);
		});

		return data;
	}

	@Override
	public UserDto updateUser(UserDto userObject, String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException("User does not Exist");

		userEntity.setFirstName(userObject.getFirstName());
		userEntity.setLastName(userObject.getLastName());
		userEntity.setEmail(userObject.getEmail());
		userEntity.setContact(userObject.getContact());

		UserEntity savedUser = userRepository.save(userEntity);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(savedUser, returnValue);

		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException("User does not Exist");

		userRepository.delete(userEntity);
	};

	@Override
	public UserDto getUserById(String userId) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException("User does not Exist");

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	};

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	};

}
