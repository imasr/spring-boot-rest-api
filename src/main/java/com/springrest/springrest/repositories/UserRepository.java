package com.springrest.springrest.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.springrest.springrest.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	// custom create method
	UserEntity findByEmail(String email);

	UserEntity findByUserId(String userId);
}
