package com.springrest.springrest.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public List<UserRest> getAllUser() {

		List<UserDto> allUsers = userService.getAllUsers();
		List<UserRest> data = new ArrayList<UserRest>();

		allUsers.forEach(item -> {
			UserRest returnValue = new UserRest();
			BeanUtils.copyProperties(item, returnValue);
			System.out.println(item);
			data.add(returnValue);
		});
		return data;
	}

	@PostMapping()
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {

		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userDetails, userDto);
		UserDto createdUserDetails = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUserDetails, returnValue);

		return returnValue;
	}

	@RequestMapping(value = "/bulk", method = RequestMethod.POST)
	public Iterable<UserRest> createBulkUser(@RequestBody Iterable<UserDetailsRequestModel> userList) {
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		userList.forEach(user -> {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			userDtoList.add(userDto);
		});
		List<UserDto> storedUsers = userService.createUserInBulk(userDtoList);
		List<UserRest> returnItems = new ArrayList<UserRest>();
		storedUsers.forEach(storedUser -> {
			UserRest items = new UserRest();
			BeanUtils.copyProperties(storedUser, items);
			returnItems.add(items);
		});

		return returnItems;
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
