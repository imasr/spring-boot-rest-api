package com.springrest.springrest.ui.model.request;

import com.springrest.springrest.io.entity.ContactEntity;

public class UserDetailsRequestModel {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String userId;

	private ContactEntity contact;

	public ContactEntity getContact() {
		return contact;
	}

	public void setContact(ContactEntity contact) {
		this.contact = contact;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	};

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	};

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	};

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	};
}
