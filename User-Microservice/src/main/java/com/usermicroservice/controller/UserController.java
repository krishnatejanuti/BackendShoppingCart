package com.usermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermicroservice.entity.User;
import com.usermicroservice.exception.UserNotFoundException;
import com.usermicroservice.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController
{
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/getByUserName/{userName}")
	public User getUserByUserName(@PathVariable String userName)
	{
		return userService.findUserByName(userName);
	}
	
	@PutMapping("/updateUser/{userId}")
	public User updateUser(@PathVariable Long userId,@RequestBody User user)
	{
		return userService.updateUser(userId, user);
	}
	
	@GetMapping("/getByUserId/{userId}")
	public ResponseEntity<User> getUserByUserId(@PathVariable Long userId) throws UserNotFoundException
	{
		User user = userService.getUserByUserId(userId);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
}
