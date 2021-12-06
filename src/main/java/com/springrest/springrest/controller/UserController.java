package com.springrest.springrest.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.springrest.services.UserService;
import com.springrest.springrest.shared.dto.UserDto;
import com.springrest.springrest.ui.model.request.UserDetailsRequestModel;
import com.springrest.springrest.ui.model.response.UserRest;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping
	public UserRest getAllUser() {
		UserRest returnValue = new UserRest();
		
		UserDto createdUserDetails= userService.getAllUsers();
		
		BeanUtils.copyProperties(createdUserDetails, returnValue);

		return returnValue;
	}

	@PostMapping
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {

		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userDetails, userDto);
		UserDto createdUserDetails= userService.createUser(userDto);
		
		BeanUtils.copyProperties(createdUserDetails, returnValue);

		return returnValue;
	}

	@PutMapping
	public String updateUser() {
		return "Update user called";
	}

	@DeleteMapping
	public String deleteUser() {
		return "Delete User called";
	}

}
