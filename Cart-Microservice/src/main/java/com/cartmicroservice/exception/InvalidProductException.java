package com.cartmicroservice.exception;

public class InvalidProductException extends Exception
{
	public InvalidProductException(String message) 
	{
		super(message);
	}
}
