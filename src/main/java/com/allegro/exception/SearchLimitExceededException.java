package com.allegro.exception;

@SuppressWarnings("serial")
public class SearchLimitExceededException extends Exception {

	public SearchLimitExceededException(String msg) {
		super(msg);
	}
}
