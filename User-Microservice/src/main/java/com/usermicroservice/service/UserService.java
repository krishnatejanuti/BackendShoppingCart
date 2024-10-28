package com.usermicroservice.service;

import java.util.List;

import com.usermicroservice.entity.User;
import com.usermicroservice.exception.UserNotFoundException;

public interface UserService 
{
	User addUser(User user);
	List<User> findAllUsers();
	User findUserByName(String userName);
	User getUserByUserId(Long userId) throws UserNotFoundException;
	String findUserToken(User userName);
	//Map<String, Object> findUserToken(User user);
	User updateUser(Long userId,User user);
	List<User> findOnlyUsers();
	
}
