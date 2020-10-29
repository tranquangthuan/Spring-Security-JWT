package com.thuan.spring.security.repository;

import org.springframework.data.repository.CrudRepository;

import com.thuan.spring.security.entity.Token;

public interface TokenRepository extends CrudRepository<Token, Long> {
	Token findByToken(String token);
}
