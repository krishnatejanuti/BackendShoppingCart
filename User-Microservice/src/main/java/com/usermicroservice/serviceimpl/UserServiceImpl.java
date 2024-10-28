package com.usermicroservice.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usermicroservice.entity.User;
import com.usermicroservice.exception.UserNotFoundException;
import com.usermicroservice.jwt.JwtService;
import com.usermicroservice.repository.UserRepository;
import com.usermicroservice.service.UserService;

@Service
public class UserServiceImpl implements UserService
{

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
	
	public User addUser(User user)
	{
		user.setPassword(encoder.encode(user.getPassword()));
	return userRepository.save(user) ;
	}

	@Override
	public List<User> findAllUsers()
	{
		
		return userRepository.findAll();
	}

	@Override
	public String findUserToken(User userName)
	{
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName.getUserName(), userName.getPassword()));
		User user=userRepository.findByUserName(userName.getUserName());
		if(authentication.isAuthenticated())
		{
			return jwtService.generateToken(user);
		}
		else
		{
			return "Login Failed";
		}
			
	}

	@Override
	public User findUserByName(String userName) 
	{
		return userRepository.findByUserName(userName);
	}

	@Override
	public User updateUser(Long userId, User user) 
	{
		User upduser = userRepository.findById(userId).orElse(null);
		upduser.setAddress(user.getAddress());
		upduser.setPhoneNumber(user.getPhoneNumber());
		upduser.setEmail(user.getEmail());
		upduser.setUserName(user.getUserName());
		
		return userRepository.save(upduser);
	}

	@Override
	public List<User> findOnlyUsers() 
	{
		List<User> userList = new ArrayList<>();
		List<User> user = userRepository.findAll();
		
		for (User u : user)
		{
			if(!u.getRole().equals("admin"))
			{
				userList.add(u);
			}
		}
		return userList;
	}
	
	@Override
	public User getUserByUserId(Long userId) throws UserNotFoundException
	{
		User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found for id :"+userId));
		return user;
	}

	/*
	 * @Override public Map<String, Object> findUserToken(User user) {
	 * Authentication authentication = authenticationManager.authenticate( new
	 * UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
	 * );
	 * 
	 * User user1 = userRepository.findByUserName(user.getUserName()); Map<String,
	 * Object> response = new HashMap<>();
	 * 
	 * if (authentication.isAuthenticated()) { String token =
	 * jwtService.generateToken(user);
	 * 
	 * Map<String, Object> userInfo = new HashMap<>(); userInfo.put("userId",
	 * user1.getUserId()); userInfo.put("userName", user1.getUserName());
	 * userInfo.put("role", user1.getRole());
	 * 
	 * response.put(token, userInfo); // Key: token, Value: user } else {
	 * response.put("Login Failed", null); // Handle failure case if needed }
	 * 
	 * return response; }
	 */

	

}
