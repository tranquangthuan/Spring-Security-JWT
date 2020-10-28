package com.thuan.spring.security.service;

import com.thuan.spring.security.entity.User;
import com.thuan.spring.security.model.UserPrincipal;

public interface UserService {
	User createUser(User user);

	UserPrincipal findByUsername(String username);
}
