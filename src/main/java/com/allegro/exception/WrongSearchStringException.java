package com.allegro.exception;

@SuppressWarnings("serial")
public class WrongSearchStringException extends RuntimeException {

	public WrongSearchStringException(String msg) {
		super(msg);
	}
}
