package com.springrest.springrest.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.springrest.exceptions.UserServiceException;
import com.springrest.springrest.services.UserService;
import com.springrest.springrest.shared.dto.PageDto;
import com.springrest.springrest.shared.dto.UserDto;
import com.springrest.springrest.ui.model.request.UserDetailsRequestModel;
import com.springrest.springrest.ui.model.response.ErrorMessages;
import com.springrest.springrest.ui.model.response.PageUsersRest;
import com.springrest.springrest.ui.model.response.Response;
import com.springrest.springrest.ui.model.response.SuccessMessages;
import com.springrest.springrest.ui.model.response.UserRest;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<Response> getAllUser(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "5") int size,
			@RequestParam(value = "sortby", defaultValue = "firstName") String sortby,
			@RequestParam(value = "sortOrder", defaultValue = "ASC") Sort.Direction sortOrder) {
		try {
			PageDto pageUserDto = userService.getAllUsers(page, size, sortby, sortOrder);
			List<UserRest> userList = new ArrayList<UserRest>();

			ModelMapper modelMapper = new ModelMapper();
			PageUsersRest userRest = modelMapper.map(pageUserDto, PageUsersRest.class);

			for (UserDto user : pageUserDto.getContent()) {
				UserRest returnValue = modelMapper.map(user, UserRest.class);
				userList.add(returnValue);
			}

			userRest.setContent(userList);
			Response response = new Response(userRest, HttpStatus.OK.value(),
					SuccessMessages.RECORD_FETCHED.getSuccessMessage());
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
			e.printStackTrace();
			Response response = new Response(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Response> getUserById(@PathVariable("userId") String userId) {
		try {
			UserDto userDto = userService.getUserById(userId);

			ModelMapper modelMapper = new ModelMapper();
			UserRest user = modelMapper.map(userDto, UserRest.class);

			Response response = new Response(user, HttpStatus.OK.value(),
					SuccessMessages.RECORD_FETCHED.getSuccessMessage());
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
			e.printStackTrace();
			Response response = new Response(null, HttpStatus.NOT_FOUND.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	@PostMapping
	public ResponseEntity<Response> createUser(@RequestBody UserDetailsRequestModel userDetails) {
		try {
			if (userDetails.getFirstName().isEmpty() || userDetails.getLastName().isEmpty()
					|| userDetails.getEmail().isEmpty() || userDetails.getPassword().isEmpty()) {
				throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
			}

			ModelMapper modelMapper = new ModelMapper();
			UserDto userDto = modelMapper.map(userDetails, UserDto.class);

			UserDto createdUserDetails = userService.createUser(userDto);

			UserRest returnValue = modelMapper.map(createdUserDetails, UserRest.class);

			Response response = new Response(returnValue, HttpStatus.CREATED.value(),
					SuccessMessages.RECORD_CREATED.getSuccessMessage());
			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (Exception e) {
			e.printStackTrace();
			Response response = new Response(null, HttpStatus.BAD_REQUEST.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
	}

	@RequestMapping(value = "/bulk", method = RequestMethod.POST)
	public ResponseEntity<Response> createBulkUser(@RequestBody Iterable<UserDetailsRequestModel> userList) {
		try {
			List<UserDto> userDtoList = new ArrayList<UserDto>();

			for (UserDetailsRequestModel user : userList) {
				ModelMapper modelMapper = new ModelMapper();
				UserDto userDto = modelMapper.map(user, UserDto.class);
				userDtoList.add(userDto);
			}

			List<UserDto> storedUsers = userService.createUserInBulk(userDtoList);

			List<UserRest> returnValue = new ArrayList<UserRest>();

			for (UserDto storedUser : storedUsers) {
				ModelMapper modelMapper = new ModelMapper();
				UserRest items = modelMapper.map(storedUser, UserRest.class);
				returnValue.add(items);
			}

			Response response = new Response(returnValue, HttpStatus.CREATED.value(),
					SuccessMessages.RECORD_CREATED.getSuccessMessage());
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			Response response = new Response(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping("/{userId}")
	public ResponseEntity<Response> updateUser(@RequestBody UserDetailsRequestModel userDetails,
			@PathVariable("userId") String userId) {
		try {

			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userDetails, userDto);

			userService.updateUser(userDto, userId);

			Response response = new Response(null, HttpStatus.OK.value(),
					userId + " " + SuccessMessages.RECORD_UPDATED.getSuccessMessage());
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
			e.printStackTrace();
			Response response = new Response(null, HttpStatus.NOT_FOUND.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Response> deleteUser(@PathVariable("userId") String userId) {
		try {

			userService.deleteUser(userId);
			Response response = new Response(null, HttpStatus.OK.value(),
					userId + " " + SuccessMessages.RECORD_DELETED.getSuccessMessage());

			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
			e.printStackTrace();
			Response response = new Response(null, HttpStatus.NOT_FOUND.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		}
	}

}
