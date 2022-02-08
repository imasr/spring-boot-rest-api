package com.springrest.springrest.shared;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	public String createNewUserId() {
		return UUID.randomUUID().toString();

	}

}
