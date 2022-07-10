package com.springrest.springrest.crud;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springrest.springrest.io.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	//custom create method
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
}
