package com.springrest.springrest.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.springrest.springrest.exceptions.UserServiceException;
import com.springrest.springrest.services.UserService;
import com.springrest.springrest.shared.dto.UserDto;
import com.springrest.springrest.ui.model.request.UserDetailsRequestModel;
import com.springrest.springrest.ui.model.response.ErrorMessages;
import com.springrest.springrest.ui.model.response.Response;
import com.springrest.springrest.ui.model.response.UserRest;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<Response> getAllUser() {
		try {
			List<UserDto> allUsers = userService.getAllUsers();
			List<UserRest> userList = new ArrayList<UserRest>();

			allUsers.forEach(item -> {
				UserRest returnValue = new UserRest();
				BeanUtils.copyProperties(item, returnValue);
				System.out.println(item);
				userList.add(returnValue);
			});
			Response response = new Response(userList, HttpStatus.OK.value(), "success");
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

			UserRest user = new UserRest();
			BeanUtils.copyProperties(userDto, user);

			Response response = new Response(user, HttpStatus.OK.value(), "success");
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

			UserDto userDto = new UserDto();

			BeanUtils.copyProperties(userDetails, userDto);
			UserDto createdUserDetails = userService.createUser(userDto);
			UserRest returnValue = new UserRest();
			BeanUtils.copyProperties(createdUserDetails, returnValue);

			Response response = new Response(returnValue, HttpStatus.CREATED.value(), "success");
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
			userList.forEach(user -> {
				UserDto userDto = new UserDto();
				BeanUtils.copyProperties(user, userDto);
				userDtoList.add(userDto);
			});
			List<UserDto> storedUsers = userService.createUserInBulk(userDtoList);
			List<UserRest> returnValue = new ArrayList<UserRest>();
			storedUsers.forEach(storedUser -> {
				UserRest items = new UserRest();
				BeanUtils.copyProperties(storedUser, items);
				returnValue.add(items);
			});

			Response response = new Response(returnValue, HttpStatus.CREATED.value(), "success");
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

			UserDto updatedUser = userService.updateUser(userDto, userId);

			UserRest returnValue = new UserRest();
			BeanUtils.copyProperties(updatedUser, returnValue);

			Response response = new Response(userDetails, HttpStatus.OK.value(),
					userId + " " + "User data updated Successfully");
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
			Response response = new Response(null, HttpStatus.OK.value(), userId + " " + "User deleted Successfully");

			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
			e.printStackTrace();
			Response response = new Response(null, HttpStatus.NOT_FOUND.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		}
	}

}
