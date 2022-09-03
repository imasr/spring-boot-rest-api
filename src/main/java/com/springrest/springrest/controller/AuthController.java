package com.springrest.springrest.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.springrest.io.entity.UserEntity;
import com.springrest.springrest.security.SecurityConstants;
import com.springrest.springrest.services.UserService;
import com.springrest.springrest.shared.dto.UserDto;
import com.springrest.springrest.shared.helper.JwtUtils;
import com.springrest.springrest.ui.model.request.UserLoginRequestModel;
import com.springrest.springrest.ui.model.response.Response;
import com.springrest.springrest.ui.model.response.UserRest;

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<Response> login(@RequestBody UserLoginRequestModel loginResuestData) throws Exception {

		try {

			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginResuestData.getEmail(),
					loginResuestData.getPassword()));

		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			Response response = new Response(null, HttpStatus.BAD_REQUEST, "Bad Credentials");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		UserDto userDto = this.userService.getUserByEmail((loginResuestData.getEmail()));
		UserRest user = new UserRest();
		BeanUtils.copyProperties(userDto, user);
		String token = this.jwtUtils.generateToken(user);
		Response res = new Response(user, HttpStatus.OK, "Login Successful");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.header(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token).body(res);

	}

}
