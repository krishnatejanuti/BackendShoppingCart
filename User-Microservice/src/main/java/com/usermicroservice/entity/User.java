package com.usermicroservice.entity;


import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class User
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(unique = true)
	private String userName;
	
	@Column(unique = true)
	private String email;
	
	@Column(unique = true)
	private String phoneNumber;
	
	private String password;
	
	private String address;
	
	private String role="user";

	 public Collection<? extends GrantedAuthority> getAuthorities() 
	 {
	        return Collections.singletonList(new SimpleGrantedAuthority(role));
	 }

	public User(Long userId2, String userName2, String role2) 
	{
	}
}
