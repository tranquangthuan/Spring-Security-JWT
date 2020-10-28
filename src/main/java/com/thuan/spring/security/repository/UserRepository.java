package com.thuan.spring.security.repository;

import org.springframework.data.repository.CrudRepository;

import com.thuan.spring.security.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
