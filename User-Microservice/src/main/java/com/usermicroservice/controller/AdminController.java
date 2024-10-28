package com.usermicroservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermicroservice.entity.User;
import com.usermicroservice.service.UserService;

@RestController
@RequestMapping("/admin/user")
public class AdminController
{
	
	@Autowired
	private UserService userService;


	@GetMapping("/getAllUsers")
	public List<User> getUsers()
	{
		return userService.findAllUsers();
	}
	
	@GetMapping("/getOnlyUsers")
	public List<User> findOnlyUsers()
	{
		return userService.findOnlyUsers();
	}
}
