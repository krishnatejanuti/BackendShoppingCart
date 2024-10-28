package com.ordermicroservice.exceptions;

public class OrderNotFoundException extends Exception
{
	public OrderNotFoundException(String msg)
	{
		super(msg);
	}
}
