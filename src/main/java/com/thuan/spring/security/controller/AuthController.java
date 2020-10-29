package com.thuan.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thuan.spring.security.entity.Token;
import com.thuan.spring.security.entity.User;
import com.thuan.spring.security.jwt.JwtUtil;
import com.thuan.spring.security.model.UserPrincipal;
import com.thuan.spring.security.service.TokenService;
import com.thuan.spring.security.service.UserService;

@RestController
public class AuthController {
	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public User register(@RequestBody User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userService.createUser(user);
	}

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return new ResponseEntity<String>("Test OK", HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		UserPrincipal userPrincipal = userService.findByUsername(user.getUsername());
		if (null == user || !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tài khoản hoặc mật khẩu không chính xác");
		}
		Token token = new Token();
		token.setToken(jwtUtil.generateToken(userPrincipal));
		token.setTokenExpDate(jwtUtil.generateExpirationDate());
		token.setCreatedBy(userPrincipal.getUserId());
		tokenService.createToken(token);

		return ResponseEntity.ok(token.getToken());
	}

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {

		return ResponseEntity.ok("hello");
	}
}
