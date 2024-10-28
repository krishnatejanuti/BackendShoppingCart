package com.cartmicroservice.exception;

public class InvalidUserException extends Exception
{
	public InvalidUserException(String message) 
	{
		super(message);
	}
}
