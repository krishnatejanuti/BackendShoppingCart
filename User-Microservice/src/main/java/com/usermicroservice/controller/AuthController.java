package com.usermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermicroservice.entity.User;
import com.usermicroservice.service.UserService;


@RestController
@RequestMapping("auth/user")
public class AuthController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping("/register")
	public User addUsers(@RequestBody User user) 
	{
		return userService.addUser(user);
	}
	
	@PostMapping("/login")
	public String loginUsers(@RequestBody User user)
	{
		return userService.findUserToken(user);

	}
	
	/*
	 * @PostMapping("/login") public Map<String, Object> findUserToken(@RequestBody
	 * User user) { return userService.findUserToken(user); }
	 */
	
}
