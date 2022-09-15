package com.springrest.springrest.services.implementation;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springrest.springrest.exceptions.UserServiceException;
import com.springrest.springrest.io.entity.AddressEntity;
import com.springrest.springrest.io.entity.UserEntity;
import com.springrest.springrest.repositories.UserRepository;
import com.springrest.springrest.services.UserService;
import com.springrest.springrest.shared.dto.AddressDto;
import com.springrest.springrest.shared.dto.PageDto;
import com.springrest.springrest.shared.dto.UserDto;
import com.springrest.springrest.shared.helper.Utils;
import com.springrest.springrest.ui.model.response.ErrorMessages;

@Service()
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
			throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

		for (int i = 0; i < user.getAddress().size(); i++) {
			AddressDto addressDto = user.getAddress().get(i);
			addressDto.setUserDetails(user);
			String publicAddressId = utils.createNewUserId();
			addressDto.setAddressId(publicAddressId);
			user.getAddress().set(i, addressDto);
		}

		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);

		String publicUserId = utils.createNewUserId();
		userEntity.setUserId(publicUserId);
		userEntity.setEncryptedPassword(bcryptPassword.encode(user.getPassword()));

		UserEntity savedUser = userRepository.save(userEntity);

		UserDto returnValue = modelMapper.map(savedUser, UserDto.class);

		return returnValue;
	}

	@Override
	public List<UserDto> createUserInBulk(List<UserDto> userList) {
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();
		ModelMapper modelMapper = new ModelMapper();

		for (UserDto user : userList) {

			for (int i = 0; i < user.getAddress().size(); i++) {
				AddressDto addressDto = user.getAddress().get(i);
				addressDto.setUserDetails(user);
				String publicAddressId = utils.createNewUserId();
				addressDto.setAddressId(publicAddressId);
				user.getAddress().set(i, addressDto);
			}

			UserEntity userEntity = modelMapper.map(user, UserEntity.class);
			String publicUserId = utils.createNewUserId();
			userEntity.setEncryptedPassword(bcryptPassword.encode(user.getPassword()));
			userEntity.setUserId(publicUserId);
			userEntityList.add(userEntity);
		}

		Iterable<UserEntity> savedUserList = userRepository.saveAll(userEntityList);
		List<UserDto> returnValue = new ArrayList<UserDto>();

		savedUserList.forEach(savedUser -> {
			UserDto userDto = modelMapper.map(savedUser, UserDto.class);
			returnValue.add(userDto);
		});

		return returnValue;
	}

	@Override
	public PageDto getAllUsers(int page, int size, String sortby, Sort.Direction sortOrder) {
		List<UserDto> data = new ArrayList<UserDto>();

		sortOrder = sortOrder == Direction.DESC ? Direction.DESC : Direction.ASC;

		int currentPage = page > 0 ? page - 1 : page;

		Pageable pageable = PageRequest.of(currentPage, size, sortOrder, sortby);

		Page<UserEntity> pageEntity = userRepository.findAll(pageable);

		ModelMapper modelMapper = new ModelMapper();
		pageEntity.getContent().forEach(item -> {
			UserDto returnValue = modelMapper.map(item, UserDto.class);
			data.add(returnValue);
		});

		PageDto pageDto = new PageDto();
		pageDto.setContent(data);
		pageDto.setPage(page);
		pageDto.setSize(size);
		pageDto.setSortby(sortby);
		pageDto.setSortOrder(sortOrder);
		pageDto.setTotalCount(pageEntity.getTotalElements());
		pageDto.setTotalPage(pageEntity.getTotalPages());

		return pageDto;
	}

	@Override
	public UserDto updateUser(UserDto userObject, String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		userEntity.setFirstName(userObject.getFirstName());
		userEntity.setLastName(userObject.getLastName());
		userEntity.setEmail(userObject.getEmail());

		UserEntity savedUser = userRepository.save(userEntity);

		ModelMapper modelMapper2 = new ModelMapper();
		return modelMapper2.map(savedUser, UserDto.class);
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		userRepository.delete(userEntity);
	};

	@Override
	public UserDto getUserById(String userId) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		ModelMapper modelMapper = new ModelMapper();
		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

		return returnValue;
	};

	@Override
	public UserDto getUserByEmail(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	};

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) {
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	};

}
